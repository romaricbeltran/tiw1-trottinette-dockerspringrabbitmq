Université Claude Bernard Lyon 1 – M2 TIW – Intergiciels et Services

# TP Microservices

## Objectifs pédagogiques

- Concevoir et construire une architecture applicative à base de microservices.
- Approfondir vos connaissances sur Docker

## Outils et ressources

Les images Docker externes dont vous partirez sont à aller chercher sur le [Docker hub](https://hub.docker.com/), de même que les docs correspondantes.

Vous utiliserez [Apache Bench](http://httpd.apache.org/docs/2.2/programs/ab.html) pour tester votre application depuis une autre machine sur le réseau. Pour l'installer en Debian/Ubuntu : <span class="code">sudo apt-get install apache2-utils</span>. Pour tester : <span class="code">ab -n 10000 -c 10 http://adresse-à-tester</span>.

Vous trouverez beaucoup d'indications utiles pour déployer vos containers sur l'architecture OpenStack dans les TPs [Swarm](http://perso.univ-lyon1.fr/fabien.rico/site/cloud:2019:tp_swarm) de TIW7.

## Préambule

Dans ce TP, vous allez reprendre votre application trottinettes et la décomposer progressivement en microservices. Pour cela, vous devez disposer d'une version fonctionnelle de votre application :
*   maintenance : avec ou sans l'ajout des batteries
*   location : Spring + Data (BD) + Security (Keycloak) + client SOAP (TP6)
*   banque : services SOAP + RabbitMQ

Rappel : prévoyez la possibilité de passer facilement d'un support de persistance en H2 à une BD externe en changeant l'URL JDBC.

### Infrastructure

Chaque binôme se verra affecté une machine virtuelle avec docker préinstallé, dont l'IP sera sur Tomuss.

## Mise en place de l'application

Dans cette section, vous allez "dockeriser" votre application et la faire tourner dans plusieurs conteneurs.

1.  Commencez par placer dans des conteneurs Docker séparés les parties "standalone" (maintenance, Keycloak, banque) de votre application, et exposez les ports de sortie sur l'interface réseau de votre VM.
2.  Reconfigurez votre application Spring Boot pour qu'elle requête ces conteneurs et exécutez-la sur votre VM pour tester.
3.  Créez un nouveau conteneur avec le SGBD de votre choix (postgreSQL, mySQL...). Vous aurez besoin de mapper les fichiers de données avec des emplacements spécifiques du système de fichiers de la machine hôte ; pour cela, utilisez des [data volumes](https://docs.docker.com/userguide/dockervolumes/). Répliquez 2 fois cette srtucture, pour la persistance des applications de maintenance, d'emprunt et de banque.
4.  Modifiez la persistence de chacune de vos applications pour qu'elles fonctionnent avec un entity manager qui utilise ce SGBD.
5.  Créez un container RabbitMQ avec l'image Docker existante sur le [Docker Hub](https://hub.docker.com/).
6.  Dockerisez l'application de banque, et configurez-la pour qu'elle utilise le container RabbitMQ.
7.  Dockerisez enfin l'application Spring Boot.
8.  Ajoutez un conteneur nginx en front et configurez nginx comme proxy pour qu'il redirige les requêtes sur les ports d'écoute des conteneurs applicatifs (maintenance, location, banque). Exposez le port d'écoute d'nginx sur la machine hôte et supprimez l'exposition de ceux des conteneurs applicatifs.

    Remarques :
    *   Faites communiquer les conteneurs avec des [overlay networks](https://docs.docker.com/network/overlay/) : vous avez le choix entre des séparations par types d'échanges (un pour les données et un pour les échanges de services) ou par domaine applicatif, ou les 2...
    *   Vous pouvez utiliser un autre conteneur, par exemple contenant un PHPMyAdmin, pour configurer votre base avant de la relier au reste de l'application, mais le plus simple est de déclarer la configuration dans les variables d'environnement d'un <span class="code">Dockerfile</span>.
    *   Plus vous mettrez de config dans des <span class="code">Dockerfile</span> plutôt que dans des commandes <span class="code">docker run</span>, plus cela vous simplifiera la vie pour la question suivante...
    *   Il est également possible d'utiliser le Dockerfile ou des volumes pour y placer des scripts d'initialisation de la base (cf doc des images [PostgreSQL](https://hub.docker.com/_/postgres)/[MariaDB](https://hub.docker.com/_/mariadb)).

### Configuration de l'application

Vous allez maintenant utiliser [Docker compose](https://docs.docker.com/compose/) pour déclarer la structure globale de votre application et en automatiser le démarrage.

Créez un fichier <span class="code">docker-compose.yml</span> qui regroupe toutes les commandes de lancement de vos conteneurs, ainsi que les ports et les overlay networks. Testez.

## Séparation des tâches dans plusieurs conteneurs applicatifs

&Agrave; partir de cette section, on considère que le métier de l'application est la location des trottinettes. La maintenance et la gestion des paiements par la banque sont considérés comme du back office. Les efforts d'optimisation ne porteront que sur la partie métier.

Actuellement, la totalité du métier de votre application (location) s'exécute dans le même conteneur. Si celle-ci est soumise à de nombreuses requêtes, ce conteneur peut être répliqué mais il occupera nécessairement beaucoup d'espace, par rapport à la tâche qu'il remplira. Vous allez "morceler" votre application dans plusieurs conteneurs, que vous pourrez répliquer indépendamment les uns des autres. &Agrave; chaque étape, vous redirigerez les requêtes depuis le front sur les conteneurs adéquats.

*   L'application comporte trois processus métier principaux : la consultation des disponibilités des trottinettes, la gestion des abonnés et celle des emprunts. Ces processus sont indépendants et n'ont pas la même charge ni les mêmes besoins de scalabilité. Ils peuvent donc être placés dans des conteneurs différents. Cependant, ils partagent des données. Choisissez une solution pour qu'ils aient tous les trois accès à un support de persistance, et que ces supports (s'ils sont plusieurs) soient synchronisés entre eux.
*   De la même manière, les différentes opérations (CRUD) sur chacun de ces processus peuvent s'exécuter dans des conteneurs différents. Pensez aux accès concurrents au support de persistance.
    
Testez plusieurs configurations et leurs performances avec Apache bench. Notez les performances pour chaque type de requête, et adoptez la plus performante d'entre elles. Indiquez ces informations dans le readme de votre projet forge.

Utilisez le repository docker de https://forge.univ-lyon1.fr (il faut l'activer pour votre dépôt) pour y stocker les images des différents conteneurs que vous avez créés.