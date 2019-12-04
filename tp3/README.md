Université Claude Bernard Lyon 1 – M2 TIW – Intergiciels et Services

# TP 3 : Spring

N'oubliez pas de faire une branche git `tp3` dans laquelle vous ferez toutes les modifications de ce TP.
Si vous travaillez à deux, créez bien ce projet dans un groupe gitlab et faites chacun un fork.

## Objectifs

Ce TP a pour objectif de coder une application en utilisant un _framework_ complet, à savoir [Spring](https://spring.io).

L'application à coder est celle du [TP2](../tp2/README.md), mais avec un server Web exposant une interface REST et enrichie avec une gestion de la sécurité.

## Spring Boot 

### Initialisation du projet

Au lieu de modifier l'application du TP2, on partira d'une nouvelle application Spring Boot initalisée via https://start.spring.io/.

On y intégrera les modules suivants: Web, Security, Data JPA


### Contexte simple et mise en place des tests

Comme le projet a été configuré pour utiliser Spring Data, il faut remplir la configuration de la base de données, même si on ne l'utilise pas dans un premier temps.

Il faut ainsi éditer le fichier `src/main/resources/application.properties` pour ajouter une _datasource_ (`spring.datasource.url`, [doc](https://docs.spring.io/spring-boot/docs/2.2.0.RELEASE/reference/html/appendix-application-properties.html#data-properties)), prendre par exemple une base H2 embarquée en mémoire (doc url JDBC [H2](http://h2database.com/html/features.html#in_memory_databases), [PostgreSQL](https://jdbc.postgresql.org/documentation/head/connect.html)).
Ne pas oublier d'ajouter une dépendance au driver JDBC dans votre `pom.xml`.

Créer un composant simple (qui ne fait pas appel à de la persistence des données), par exemple le composant qui récupère les informations sur les trottinettes.
On n'hésitera pas à reprendre une partie du code du TP2 pour implémenter ce composant.

Mettre en place des tests mettant en oeuvre le composant en l'injectant dans la classe de test générée par SpringBoot.

**Remarque:** l'injection dans une classe de test se fait comme l'injection dans un composant classique Spring.

### Data

Implémenter le reste des fonctionalités décrites dans le TP2.
Pour cela créera des composants en prenant le parti de tout gérer en base de données via JPA.

Tester en java (test unitaires et/ou tests d'intégration avec l'_ApplicationContext_ Spring fourni via `@SpringBootTest`).

### API Rest

Mettre en oeuvre une API REST pour votre application en utilisant des composants `@RestController` Spring.

> L'application est sécurisée par défaut avec un mot de passe aléatoire, voir [ici](https://docs.spring.io/spring-security/site/docs/5.2.1.RELEASE/reference/htmlsingle/#starting-hello-spring-security-boot).

Décrire cette API via un fichier [OpenAPI](https://swagger.io/specification/). 
Ce fichier pourra être écrit à la main ou généré via [SpringFox](http://springfox.github.io/springfox/docs/current/).

Utiliser le générateur de test jmeter à partir de ce fichier via [Swagger Codegen](https://swagger.io/tools/swagger-codegen/).  
Il est possible d'utiliser (et même recommandé) le [plugin maven swagger-codegen](https://github.com/swagger-api/swagger-codegen/tree/master/modules/swagger-codegen-maven-plugin) qui simplifiera l'utilisation de cet outil à condition d'intégrer les éléments de configuration suivants dans le `pom.xml` (les emplacements où insérer le code sont indiqués en syntaxe XPath):

dans `/project/properties`, ajouter:
```xml
<jaxb-substitute.api.version>1.0.4.Final</jaxb-substitute.api.version>
```

dans `/project/build/pluginManagement/plugins`, ajouter:
```xml
<plugin>
    <groupId>io.swagger</groupId>
    <artifactId>swagger-codegen-maven-plugin</artifactId>
    <version>2.3.1</version>
    <dependencies>
        <dependency>
            <groupId>javax.xml.bind</groupId>
            <artifactId>jaxb-api</artifactId>
            <version>2.3.1</version>
        </dependency>
    </dependencies>
</plugin>
```

dans `/project/build/plugins`, ajouter la configuration du plugin en s'inspirant de la [documentation](https://github.com/swagger-api/swagger-codegen/blob/master/modules/swagger-codegen-maven-plugin/README.md), par exemple:
```xml
<plugin>
    <groupId>io.swagger</groupId>
    <artifactId>swagger-codegen-maven-plugin</artifactId>
    <version>2.3.1</version>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <inputSpec>${project.basedir}/src/main/resources/swagger.yaml</inputSpec>
                <language>jmeter</language>
            </configuration>
        </execution>
    </executions>
</plugin>
```

Une fois les tests générés, on peut les recopier dans un répertoire versionnés et changer les données CSV.
Le plugin génère des tests utilisant le module [CSV_Data_Set](https://jmeter.apache.org/usermanual/component_reference.html#CSV_Data_Set_Config). 
Il faudra compléter la configuration du test pour injecter les variables CSV au bon endroit.


## Sécuriser avec Spring Security

On souhaite gérer l'authentification via [Keycloak](https://www.keycloak.org), un serveur permettant de gérer des identité et auquel on peut déléguer l'authentification via OAuth.

Installer Keycloak sur votre machine: soit directement un [serveur standalone](https://www.keycloak.org/downloads.html), soit en utilisant le [conteneur docker officiel](https://hub.docker.com/r/jboss/keycloak).

Le [Spring Boot Adapter de Keycloak](https://www.keycloak.org/docs/latest/securing_apps/index.html#_spring_boot_adapter) permet d'utiliser Keycloak comme source d'authentification. 
Il peut être intégré avec Spring Security.

> Voir le tutoriel "[A Quick Guide to Using Keycloak with Spring Boot](https://www.baeldung.com/spring-boot-keycloak)" pour la configuration de Keycloak avec Spring Security, plus la [documentation](https://www.keycloak.org/docs/latest/securing_apps/index.html#_spring_security_adapter)
>
> Merci à Louis pour les infos suivantes (cf issue #1):
>
> - Attention aux URL: bien indiquer votre projet et pas master dans l'url, par exemple `http://localhost:8180/auth/realms/SpringBootKeycloak/protocol/openid-connect/token` et **pas** `http://localhost:8180/auth/realms/master/protocol/openid-connect/token`.
> - Le formulaire à envoyer en post pour obtenir un token d'authentification doit être de type `x-www-form-urlencoded` et le `client_id` est l'id que l'on a créé dans l'onglet Clients (ici `login-app`)
> - Utiliser la version 4.4.0.Final pour la dépendance `keycloak-adapter-bom`
> - Il faut ajouter les lignes suivante dans `application.properties`, en supposant que votre realm est  `SpringBootKeycloak` et que le port de keycloak est `8180`, que votre application soit référencées par (i.e. le `client_id` est) `login-app` dans Keycloak:
>   ```properties
>   spring.main.allow-bean-definition-overriding=true
>   
>   keycloak.auth-server-url=http://localhost:8180/auth/realms/SpringBootKeycloak/protocol/openid-connect/token
>   keycloak.realm=SpringBootKeycloak
>   keycloak.resource=login-app
>   keycloak.public-client=true
>   keycloak.principal-attribute=preferred_username
>   ```

Sécuriser l'application avec un rôle d'administrateur pouvant consulter et modifier toutes les ressources et un rôle utilisateur pouvant consulter et modifier uniquement ses propres données (données d'abonnés et emprunts réalisés).

### Spring AOP

À venir

## Divers à éventuellement intégrer

(Use case logs: composant de facturation fourni, mais buggé à intégrer, puis débugger avec les logs)

