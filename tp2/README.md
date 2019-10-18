Université Claude Bernard Lyon 1 – M2 TIW – Intergiciels et Services

# TP 2 : Conteneurs d'objets et inversion de contrôle

N'oubliez pas de faire une branche git `tp2` dans laquelle vous ferez toutes les modifications de ce TP.
Si vous travaillez à deux, créez bien ce projet dans un groupe gitlab et faites chacun un fork.

## Objectifs pédagogiques

- Mettre en pratique différents patterns (IoC, Contexte, Annuaire, Object pool...) afin de mieux comprendre le fonctionnement d'un framework.
- Mettre en place un outil configurable et capable de gérer le cycle de vie de ses composants. 

## Code fourni : système d'emprunt de trottinettes

Dans ce TP, vous allez transformer cette application comme si elle s'exécutait côté serveur, et petit à petit, construire les différents éléments d'un framework autour d'elle.

Voici la façon dont elle fonctionnera :

- Le contenu qui vous a été donné dans le projet `emprunt` ainsi que les DAOs que vous avez développés au TP1 pour accéderà la table contenant les trottinettes se trouveront côté serveur.

  **Vous devez avoir démarré le serveur Jetty et Spring du projet Maintenance avant de lancer celui de ce TP.**

- Dans ce TP, le client sera simulé par les tests. Une bonne partie sont déjà écrits, mais vous les ferez évoluer avec l'application. Ces tests adresseront directement le serveur et non les classes métier.

Dans ce TP, vous aurez 3 types de données à manipuler avec chacune une durée de vie différente :
1) Les trottinettes, qui sont supposées exister et ne pas varier tout au long de ce TP (on ne s'intéresse pas aux pannes ni aux rechargements) ; le chargement de la liste des trottinettes est réalisé en interrogeant le serveur Web du TP1
2) Les abonnés, qui s'inscrivent pour une durée définie
3) Les emprunts de trottinettes, qui peuvent être créés par les abonnés à tout moment, mais ne peuvent jamais être supprimés.

## Premières manipulations

Créez un package `serveur` dans lequel vous placerez une classe `Serveur`. Cette classe interfacera les clients avec l'ensemble des objets de l'application. Dans un premier temps, cette classe aura pour rôle :

- d'instancier les objets qui seront passés à la création du système de gestion des abonnés et des emprunts,
- de servir de pattern contrôleur pour gérer toutes les requêtes arrivant au serveur

Faites en sorte de permettre les opérations suivantes :

### Gestion des trottinettes

- Interrogation de la disponibilité d'une trotinette

### Gestion des abonnés

Les abonnés sont persistés dans un fichier (XML ou JSON). Le nom de ce fichier sera passé en paramètre du DAO permettant d'y accéder.

Mettez en place les use case suivants :

- abonnement
- désabonnement

### Gestion des emprunts

Rajoutez une table à la base pour y stocker les emprunts. Mettez en place les use case suivants :

- création
- interrogation par date

### Autres manipulations

- Utilisez le pattern DTO pour simplifier l'interface du serveur et permettre de créer facilement des emprunts.
- Vous pouvez aussi rajouter les getters nécessaires pour permettre à Emprunt de renvoyer les informations sur ses attributs.
- Rajoutez une méthode dans Serveur qui retourne l'instance de `EmpruntDTO` créée.

Testez.

À ce stade, vous devez avoir un serveur capable de gérer les abonnés et de créer des emprunts.

## 1. Inversion de contrôle

Vous allez maintenant mettre en place un conteneur et remplacer toute la logique d'injection de dépendances "directe" que vous avez créée à la section précédente par un mécanisme de résolution de dépendances. Pour cela :

- Créez un conteneur (voir indications ci-dessous)
- Placez le contrôleur et tous les objets dont il a besoin pour s'instancier dans ce conteneur.

### Indications

- Conteneur :
La classe Serveur instanciera un conteneur qui sera basé sur l'outil [PicoContainer](http://picocontainer.com/).
Alternativement : 
  - Solution à préférer : vous ajouterez la [dépendance Maven sur la version 2.15 de Picocontainer](https://search.maven.org/artifact/org.picocontainer/picocontainer/2.15/jar)
  - Défaut : vous téléchargerez la dernière version du jar [sur le site](http://central.maven.org/maven2/org/picocontainer/picocontainer/2.15/picocontainer-2.15.jar) ou la version disponible en local [ici](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/TP1/picocontainer-2.15.jar.)

  La doc est disponible [sur le site](http://picocontainer.com/) et une Javadoc de la version 2.14.3 [en local](https://perso.liris.cnrs.fr/lionel.medini/enseignement/CAHD/TP1/picocontainer-2.14.3-javadoc.jar). Commencez par lire la première page d'introduction à l'utilisation de cet outil, située [ici](http://picocontainer.com/introduction.html).
- Arbre de dépendances (pour l'instant, il est assez plat) :

  ```
  Controleur -> String contenant le nom de la compagnie de location
  Controleur -> List<Trottinette>
  Controleur -> AbonneDao
  Controleur -> EmpruntDao
  ```

Remplacez l'instance de `AbonneDao` créée "à la main" par le serveur par la classe elle-même. On suppose ici que son constructeur prend une string en paramètre (le nom du fichier). [Désambiguïsez](http://picocontainer.com/disambiguation.html) les noms dans les paramètres des constructeurs pour que PicoContainer soit capable de résoudre le référentiel de dépendances.

- Méthodes de la classe `Controleur` : 
  - méthodes de gestion du cycle de vie : faites en sorte que la classe `Controleur` implémente l'interface Startable et ajoutez-y les méthodes requises ; dans la méthode start(), rajoutez un affichage indiquant que le serveur a démarré, le type de classe d'implémentation du `Controleur` (aide : utilisez l'API Reflection) et l'instance du DAO qu'il utilise pour accéder aux données (aide : méthode toString() de l'instance). L'objectif est d'obtenir un affichage du style :

  ```Composant Controleur démarré. Objet d'accès aux données : fr.univlyon1.m2tiw.tiw1.metier.contôleur.dao.AbonneDAO@95c083```
  
- Méthodes de la classe `Serveur` : 
  - constructeur : il instanciera un `DefaultPicoContainer`, puis y rajoutera les composants avec des dépendances entre eux. Il récupèrera ensuite le composant `Controleur` instancié par le conteneur, le stockera dans une variable globale et appellera sa méthode start().
  - méthode (provisoire) de service : getControleur(), renvoyant au client une référence vers l'instance de `Controleur`.

> À ce stade, vous avez inversé le contrôle de vos objets métier en les plaçant dans un serveur (i.e. un framework) qui se charge d'instancier, de gérer et d'utiliser ces objets.

Ne vous préoccupez pas pour l'instant des méthodes permettant de gérer les abonnés ou les emprunts.

## 2. Isolation et uniformisation des objets côté serveur

### 2.1. Isolation

Bien entendu, vous ne pouvez pas laisser le client accéder directement à l'instance du contrôleur créée dans le conteneur. Pour éviter cela, vous allez implémenter le paradigme requête-réponse :

- Modifiez la méthode de service du serveur pour qu'elle soit plus générique ; par exemple :

  `public Object processRequest(String commande, Map<String, Object> parametres);`

  où les éléments de la Map représentent les paires nom / valeur des paramètres des requêtes
- Dans le contrôleur, passez les méthodes add, remove, get... en privé, et créez une méthode publique `process()`, qui appellera chacune de ces trois méthodes en fonction de la commande
- Modifiez vos tests pour qu'ils n'appellent plus que la méthode `processRequest()` du serveur
- Renommez la classe `Serveur` en `ServeurImpl`, extrayez l'interface de service du serveur (que vous appellerez `Serveur`) et faites en sorte que les clients (les tests) ne connaissent plus que cette interface.

> Remarquez que la classe `ServeurImpl` masque désormais complètement l'implémentation du traitement des requêtes par les objets métier. Il suffit au client de connaître son API pour utiliser l'application.

### 2.2. Uniformisation

Dans cette partie, vous allez modifier le référentiel de dépendances pour vous rapprocher d'un fonctionnement en MVC.

Plutôt que d'avoir un objet `Controleur` qui répond à différentes requêtes, vous allez créer plusieurs objets sur le même modèle, mais traitant chacun un type de requête spécifique (i.e. des contrôleurs délégués / de cas d'utilisation / de ressources). Pour cela :
- Commencez par définir une interface et une classe abstraite reprenant les principales caractéristiques des composants : dépendances, implémentation de `Startable`
- Créez les classes contrôleur qui correspondent au découpage que vous avez choisi et faites en sorte qu'elles implémentent une méthode de service `process()`
- Créez les classes implémentant le modèle et correspondant aux méthodes de service  `getTrottinette()`, `addAbonne()`, `removeAbonne()`, `getAbonne()`, `createEmprunt()`, etc. Vous regrouperez les fonctionnalités dans des classes représentant des ressources (et non des opérations) liées aux objets métier, par exemple une classe `TrottinetteResource` pour la consultation de la disponibilité des trottinettes, une classe `AbonneResource` pour la création / suppression / consultation des abonnés et une classe `EmpruntResource` pour la création des emprunts. Pour cela, il faut que vous ajoutiez un paramètre supplémentaire (la notion de "méthode"), qui indique l'opération à réaliser sur la ressource.
- Modifiez le serveur pour que votre conteneur crée les composants correspondants aux instances de vos nouvelles classes
- Au niveau du serveur, créez une méthode d' "aiguillage" des requêtes vers les instances de chacun des contrôleurs, qui sera appelée par la méthode de service du serveur : la commande correspond au nom de la classe à appeler, comme un nom de ressource sur un serveur Web. Créez ensuite (et factorisez dans la classe abstraite), le mécanisme qui appellera la bonne méthode de la classe, en fonction de la valeur du paramètre "méthode" défini plus haut.

**Normalement, votre application ne doit pas fonctionner :** le container vous renvoie une liste vide à chaque opération et les instances des DAO sont différentes dans les messages d'initialisation des méthodes de gestion du cycle de vie.

Cela vient du fait que bien que les List, TrottinetteDao et AbonneDao, etc. soient des dépendances communes de tous les `XxxRessource` du conteneur, par défaut, celui-ci résout les dépendances en instanciant un objet différent pour chaque instance de `XxxRessource`. Toutefois, vous pouvez indiquer que vous souhaitez procéder autrement, c'est-à-dire qu'il "cache" les instances. Vous pouvez résoudre ce problème à deux niveaux :

1.	Au niveau du composant : en spécifiant la caractéristique "Cache" des composants que vous voulez cacher. Le plus simple est d'utiliser la méthode `as()` du conteneur, comme spécifié [ici](http://picocontainer.com/properties.html).
2.	Au niveau du conteneur : en spécifiant un [comportement](http://picocontainer.com/behaviors.html) global de type "Caching" pour tous les composants du conteneur dans le constructeur de celui-ci.

Si vous choisissez la seconde solution, les ressources seront cachées également, et vous n'aurez plus besoin de les stocker dans une variable globale. Par ailleurs, comme indiqué dans le warning du début de la [page sur la gestion du cycle de vie des composants](http://picocontainer.com/lifecycle.html), les méthodes `start()`, `stop()`, etc. sont conçues pour fonctionner avec des composants cachés, et vous pourrez appelez directement la méthode `start()` du conteneur pour qu'il démarre tous les composants qui implémentent `Startable` en même temps...

> À ce stade, vous avez réalisé un outil équivalent à un conteneur de servlets. Il pourra fonctionner avec n'importe quel type de ressource (au sens ReST) utilisé par l'application, effectuer différentes opérations (correspondant à des méthodes HTTP), à partir du moment où celle-ci est déclarée dans le serveur et correspond à une commande reconnue.

## 3. Création d'un contexte applicatif

Dans cette partie, vous allez rajouter un niveau d'indirection entre le conteneur (et ses composants) et leurs dépendances (par exemple, les objets de type DAO). Pour cela, vous allez implémenter une classe qui permettra à chaque instance de `XxxRessource` créée d'accéder au DAO en respectant le pattern Context présenté en cours. Cela permettra par exemple d'utiliser un DAO instancié à l'extérieur du serveur (cas d'une connexion à un SGBD), ou de modifier par configuration le DAO utilisé pour gérer la persistence des données de l'application.

### 3.1. Création du contexte

Créez une interface `AbonneContext`, de façon à ce que :

- le serveur puisse indiquer une référence à un objet `AbonneDAO` existant
- les différents composants puissent obtenir une référence sur ce DAO

En clair, il s'agit d'un objet qui stocke une référence sur un DAO et possède deux accesseurs sur ce champ.

Créez une classe d'implémentation `AbonneContextImpl` de cette interface, instanciez-la dans votre serveur et ajoutez-la en tant que composant de votre conteneur.

### 3.2. Modification de l'arbre de dépendances

Vous allez modifier les composants du conteneur ayant une dépendance sur un objet `AbonneDAO` pour qu'ils dépendent de `AbonneContext`.

Remarque : tant qu'à faire, servez-vous de la documentation de PicoContainer pour utiliser un autre type d'injection de dépendances que par constructeur.

- Modifiez les constructeurs de vos classes d'implémentation de `Emprunt` (abstraites ou non), de façon à ce qu'ils prennent en paramètre un `AbonneContext` et non plus un `AbonneDAO`.
- Dans les composants, récupérez le DAO par des appels à la méthode correspondante (`getDAO()` ?) du contexte avant leur démarrage (méthode `start()`)
- L'instance du DAO sera récupérée par le serveur dans le conteneur à l'aide d'un `getComponent()`, et ajoutée au contexte à l'aide d'une méthode spécifique (`setDAO` ?).

### 3.3. Initialisation et utilisation du contexte

Enfin, mettez en place les méthodes correspondantes du contexte de façon à ce que les classes d'implémentation de `Controleur` puissent définir et récupérer une référence sur le DAO (`setDAO()`, `getDAO()` ?) .

Testez votre application. Vous pouvez ensuite par exemple vous servir du contexte pour filtrer les appels au DAO et ne renvoyer la bonne référence que si la méthode est appelée par une instance de type `XxxRessource` (voir [ici](http://www.javalobby.org/java/forums/t67019.html) ou [là](http://stackoverflow.com/questions/421280/in-java-how-do-i-find-the-caller-of-a-method-using-stacktrace-or-reflection) pour des exemples de code sur comment trouver la classe appelant une méthode).

Remarque : dans ce cas, supprimez l'appel à la méthode `toString()` de l'instance du DAO dans l'affichage de la méthode `start()` des composants.

### 3.4 Généralisation du contexte

Actuellement, votre contexte n'est capable que de gérer un `AbonneDAO`. Modifiez-en l'API pour qu'il puisse stocker des références à tous les types d'objets et que ces objets soient accessibles par un nom (String).

Testez ce contexte générique en lui injectant aussi la liste des salles et en forçant le passage par le contexte pour permettre aux `XxxRessource` d'obtenir cette liste.

> Votre serveur a désormais une responsabilité supplémentaire : en plus de fournir un conteneur de composants métier, il gère un contexte pour l'isolation des composants du conteneur. Le contexte est accessible à l'intérieur du conteneur pour permettre et contrôler l'accès par les composants aux éléments externes tels que le DAO. Vous avez mis en place les principaux éléments d'un framework applicatif, que vous allez perfectionner dans la suite.

## 4. Création d'un Annuaire

Encapsulez le contexte dans une structure de type annuaire (cf. cours). Un annuaire sera une hiérarchie de contextes, spécifiques à différents éléments de votre conteneur et de vos applications. Ces éléments correspondront aux différents types d'objets à isoler (les `XxxRessource`, la liste de trottinettes et les DAOs). Faites en sorte que votre annuaire soit capable de décomposer les noms de la manière suivante : nom de contexte + "/" + nom d'objet stocké.

Vous allez maintenant commencer à remplir votre annuaire. Bindez :

- dans le contexte racine de l'annuaire : le serveur
- dans un contexte spécifique à l'application : les objets "de premier niveau" (ici, les différents `XxxRessource`), c'est-à-dire qui répondent aux requêtes du client,
- dans un sous-contexte du précédent : les objets métier spécifiques à l'application (ici, la liste)
- dans un sous-contexte du contexte de l'application, spécifiquement dédié à la persistence : les instances des différents Dao

Remarque : les bindings dans l'annuaire s'effectuent avec des références à des instances déjà créées. Ces instances doivent donc être déjà créées (éventuellement par le conteneur), l'annuaire étant uniquement un moyen de permettre les accès à ces objets.

Modifiez vos tests fonctionnels pour qu'ils démarrent séparément l'annuaire et le serveur, que le serveur s'enregistre dans l'annuaire, mais que les tests ne disposent que d'une référence sur l'annuaire. Faites en sorte qu'au démarrage, ils "découvrent" le serveur en passant par l'annuaire.

> Vous venez de construire quelque chose de similaire à un annuaire JNDI, qui pernet aux composants d'accéder à des références sur des objets interne au conteneur ou distants. L'avantage de cette méthode est qu'elle fonctionne quelles que soient les implémentations du conteneur et du composant, et qu'elle permet d'utiliser plusieurs implémentations différentes d'un objet pour une même interface.

### Aspects dynamiques de l'annuaire

Actuellement, vos objets interrogent l'annuaire pour récupérer des références à d'autres objets. Cependant, il peut arriver qu'une référence sur un objet change, par exemple parce qu'un objet n'est plus disponible ou qu'une nouvelle version a été implémentée. Vous allez donc mettre en place un système à base d'événements qui permettra aux objets clients de s'abonner aux changements des références dans l'annuaire et donc de faire une nouvelle requête à l'annuaire à chaque notification.

Pour cela, modifiez l'implémentation de votre annuaire :

- mettez en place un pattern Observer
- faites en sorte que les objets puissent s'abonner aux événements "changement de référence sur le nom X" pour réagir en conséquence
- déclenchez cet événement à chaque rebind d'un objet sur un nom existant

> Cette stratégie de mise à jour dynamique d'une référence sur un objet est celle utilisée dans les frameworks à composants dynamiques, comme OSGi. Bien entendu, cette stratégie fonctionnera d'autant mieux si le client s'attend à trouver une implémentation d'une interface et non une instance d'une classe.

## 5. Mise en place d'un serveur d'applications

Dans cette partie, vous allez rendre votre serveur générique et lui permettre d'exécuter différentes applications.

### 5.1 Configuration de l'application

Écrivez un fichier de configuration en XML (ou JSON) et stockez-y les dépendances de valeurs (type d'objet DAO, nom du fichier de stockage) et les types d'objets `XxxRessource` correspondant à chaque commande (à la manière des fichiers web.xml utilisés dans un container de servlets). Ci-dessous un exemple de fichier de configuration :

```
{
  "application-config": {
    "name": "my-trottinettes",
    "business-components": [
      {"class-name": "monPackage.TrottinetteRessource"},
      {"class-name": "monPackage.AbonneRessource"},
      {"class-name": "monPackage.EmpruntRessource"}
    ],
    "service-components": [
      {"class-name": "monAutrePackage.AnnuaireImpl"}
      {"class-name": "java.util.ArrayList"}
    ],
    "persistence-components": [
      {
        "class-name": "monTroisiemePackage.AbonneDAO",
        "params": [{
          "name": "file",
          "value": "sample-data/abonnes.json"
        }]
      }, {
        "class-name": "monTroisiemePackage.EmpruntDAO",
        "params": [{
          "name": "base",
          "value": "H2"
        }]
      }
    ]
  }
}
```
Remarque : dans le fichier de configuration, vous pouvez également indiquer :

- les noms des composants pour leur stockage dans l'annuaire
- les règles de contrôle d'accès à faire appliquer aux contextes dans lesquels ils se trouvent

Utilisez ces données dans la classe Serveur lors de l'instanciation des éléments des conteneurs et du contexte. Vous devrez utiliser l'API Reflection (et probablement `Class.forName()`) pour récupérer le .class défini par une chaîne de caractères.

### 5.2 Spécialisation des composants (bonus)

Éventuellement, vous pouvez spécialiser vos classes `XxxRessource` et définir différents types de composants :

- Contrôleurs : ce seront les composants en front du serveur. Ils devront répondre à un type de requête du client, et organiser les traitements réalisés par les autres composants
- Vues : elles récupèreront les données fournies par les modèles et les formatteront pour qu'elles répondent aux attentes du client
- Modèles : ces composants réaliseront les traitements métier. Ils pourront faire appel à d'autres composants, de type entités
- Entités : ils représentent les données métier destinées à être liées à un support de persistance (`Abonne`, `EmpruntDTO`)

Pour mettre en oeuvre cette spécialisation, vous pouvez soit faire hériter chaque type d'une interface spécifique, soit les annoter en fonction d'annotations définies en conséquence.

De la même manière, pour prendre en compte cette spécialisation au niveau du serveur, vous pouvez soit modifier ce serveur (et son mode de configuration) pour que le fichier de configuration mentionne la nature des composants, et que le serveur la "comprenne", soit rajouter un composant intermédiaire qui intercepte toutes les requêtes et s'appuie sur son propre mode de configuration pour instancier et rediriger les requêtes sur ces composants.

> À ce stade, vous avez réalisé un serveur d'applications, composé d'un serveur et d'un framework capable de mettre en place et de faire tourner différents types d'applications. Si vous avez réalisé la partie 5.2 en modifiant le serveur, vous avez créé un serveur qui fonctionne d'une manière proche des serveurs Java EE. Si vous l'avez réalisée par ajout d'une couche supplémentaire entre le serveur de la question 4 et l'application, votre serveur se rapproche plus d'un conteneur Spring inclus dans un conteneur de servlets.

## 6. Métaprogrammation

Dans cette partie, vous allez ajouter des annotations pour :

- configurer vos applications
- générer du "boilerplate code"

### 6.1. Utilisation d'une annotation "custom"

- Ouvrez le projet `annotations` avec votre ID. Lisez le code et générez un jar.
- Utilisez l'annotation `@Todo` dans le code de votre projet Emprunt.
- &Agrave; l'aide des slides du cours, configurez le pom.xml du projet Emprunt pour faire en sorte d'utiliser l'API Pluggable Annotation Processing.
  En particulier, il faut ajouter une dépendance sur le module annotations comme suit:
  ```xml
  <dependency>
    <groupId>tiw1</groupId> 
    <artifactId>annotations</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/../annotations/target/annotations-1.0-SNAPSHOT.jar</systemPath>
  </dependency>
  ```
  et modifier la configuration du plugin de compilation:
  ```xml
  <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.6.2</version>
    <configuration>
      <annotationProcessors>tiw1.annotation.processors.TodoProcessor</annotationProcessors>
    </configuration>
  </plugin>
  ```
- Recompilez le projet Emprunt et vérifiez la présence du fichier Todos.html à la racine du projet

### 6.2. Réalisation d'annotations

Utilisez la même méthode pour :

<!-- - éliminer les méthodes "parasites" `start()` et `stop()` à l'aide d'une annotation `@Startable`
- réaliser l'injection de dépendances (`@Inject`) -->
- déclarer des composants du framework et les spécialiser (`@Component`, `@Controller`...)

## 7. Pooling 

Dans cette partie vous allez constituer un _pool_ de `Trottinette` qui va remplacer la liste des trottinettes.

- Créer une classe `TrottinettePool` contenant la liste des trottinettes.
- Y ajouter des méthodes pour récupérer et rendre une trottinette.
- Verrouiller les trottinettes utilisées en rendant impossible sa récupération tant qu'elle n'a pas été rendue.
- Modifier les composants utilisant la liste de trottinettes pour utiliser le pool à la place
- Bien penser à faire du pool un composant.
- Tester le verrouillage des trottinettes dans un test unitaire.  

## 8. Intercepteurs

Dans cette partie vous allez constituer un système d'intercepteurs devant vos ressources:

- Créer une interface `Intercepteur` avec deux methodes:
  - une méthode `input` prenant les mêmes arguments que `process`, mais renvoyant une `Map<String, Object>` de paramètres éventuellements modifiés ou enrichis.
  - une méthode `output` prenant les mêmes arguments que `process` ainsi qu'un `Object` représentant un résultat. Cette méthode renverra également un `Object`.
- Créer une interface et une implémentation pour une chaîne d'intercepteurs qui appelera une liste d'intercepteur en séquence comme vu en cours.
- Créer une classe intercepteur pour logger les requêtes, et l'utiliser avec une chaîne d'intercepteurs. 
  Modifier le contrôleur principal pour qu'il appelle la chaîne d'intercepteurs avant d'appeler la méthode `process` des contrôleurs délégués avec les nouveaux paramètres ainsi obtenus.
- Bonus: créer un intercepteur qui traduit automatiquement en objet Java le paramètre `body` en supposant qu'il s'agit à l'origine d'une String contenant des données JSON. Typiquement, on aimerait ici extraire automatiquement un EmprunDTO.
  La méthode `output` de cet intercepteur traduira les objets réponse en JSON.
  

## Instructions de rendu

**Ce TP est à rendre pour le dimanche 20 octobre 2019** (date du dernier push / merge sur la forge sur / depuis la branche tp2).
