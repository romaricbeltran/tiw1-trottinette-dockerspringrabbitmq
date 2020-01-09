Université Claude Bernard Lyon 1 – M2 TIW – Intergiciels et Services

# TIW1 - Intergiciels et services

Ce dépôt regroupe les ressources pour l'UE [intergiciels et services](http://offre-de-formations.univ-lyon1.fr/ue-16806-345%2Fintergiciels-et-services.html) du master [TIW](http://master-info.univ-lyon1.fr/TIW/).

## Supports de cours

- [Introduction](http://emmanuel.coquery.pages.univ-lyon1.fr/slides/tiw1-01-introduction/#/)
- [Conteneurs et IoC](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/CM/CM_IS_conteneurs.pdf)
- [Contexte et annuaire](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/CM/CM_IS_contexte.pdf)
- [Métaprogrammation](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/CM/CM_IS_metaprogrammation.pdf)
- [Pooling](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/CM/CM_IS_pool.pdf)
- [Intercepteurs](http://emmanuel.coquery.pages.univ-lyon1.fr/tiw1-is/tiw5-handlers.pdf)
- [Transactions JTA, Spring Data & Spring Security](http://emmanuel.coquery.pages.univ-lyon1.fr/slides/tiw1-08-spring-data-security/)
- Composants distribués, [SOAP](https://perso.liris.cnrs.fr/emmanuel.coquery/enseignement/tiw1-is/web-services-03a-messages.pdf), [WSDL](https://perso.liris.cnrs.fr/emmanuel.coquery/enseignement/tiw1-is/webservices-03b-wsdl.pdf)
- [Bus de messages et EIP](http://emmanuel.coquery.pages.univ-lyon1.fr/slides/tiw1-10-messages-eip)
- [Microservices](https://perso.liris.cnrs.fr/lionel.medini/enseignement/IS/CM/CM_IS_microservices.pdf)
- [Orchestration de conteneurs](http://emmanuel.coquery.pages.univ-lyon1.fr/tiw1-is/OrchestrationConteneursK8S.pdf)

> À venir
    
## Matériel et logiciel

Merci de prévenir au plus tôt les enseignants si vous n'utilisez pas votre propre ordinateur en TP.

Pour votre propre ordinateur, prévoyez d'installer le [JDK en version 11](https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot), un IDE comme [IntelliJ IDEA](https://www.jetbrains.com/idea/) (licence étudiant disponible avec votre mail Lyon 1) ou [Eclipse](https://www.eclipse.org/).
Sous Windows, prévoir la possibilité d'exécuter des scripts ``bash`` et de pouvoir facilement vous connecter en SSH sur une machine.
Début janvier, il sera intéressant de disposer de [Docker](https://www.docker.com/). 
    
## Sujets de TP

- [TP 1 : Mise en route Java](tp1/README.md) (séances des 11/09/2019 et 12/09/2019)
- [TP 2 : Design patterns & frameworks](tp2/README.md) (du 18/09/2019 au 17/10/2019)
- [TP 3 : Spring](tp3/README.md) (du 06/11/2019 au 13/11/2019)
- TP 5 : OSGi et chargement dynamique (séance du 14/11/2019)
- [TP 6 : Services Web SOAP](tp6/README.md) (séances des 04/12/2019 et 05/12/2019)
- TP 7 : Files de messages et annuaires (séances des 11/12/2019 et 12/12/2019)
- TP 8 : Microservices avec Docker (séance du 08/01/2020)
- TP 9 : Orchestration avec Kubernetes (séances des 15/01/2020 et 16/01/2020)
- TP 10 : Benchmarking et cache (séances des 05/02/2020 et 06/02/2020)

## Consignes pour le rendu des TPs

Tous les TPs de sont pas à rendre. En cas de doute, n'hésitez pas à envoyer un mail pour savoir ce qu'il en est.
Les TP à rendre préciseront une date de rendu.

Pour chaque TP à rendre, il faudra indiquer dans tomuss l'URL **HTTPS** de clone du dépôt dans lequel vous avez travaillé.
Dans ce dépôt, vous aurez pris soin de créer une branche ``tpx`` où ``x`` est le numéro du TP concerné.
C'est cette branche qui sera utilisée pour évaluer le TP.
Vous êtes responsable de la création de cette branche. 
Si le dépôt indiqué sur tomuss ne contient pas cette branche, le TP pourra être considéré comme non rendu (et donc une note de 0 au TP).

Si vous travaillez en binôme, veillez à créer le dépôt dans un groupe de la forge. 
Astreignez vous à travailler en ayant chacun votre _fork_ du projet et en utilisant le système de _merge request_ de Gitlab.
Attention à ne pas oublier de gérer les _merge request_ avant le rendu.
    

## Fil rouge

L'ensemble des TP de cette UE sera illustré en se plaçant dans le cadre d'une entreprise de location de trottinettes électriques.
Selon le TP, on abordera différentes facettes du système d'information (simplifié) d'une telle entreprise.
Par exemple, le [premier TP](tp1/README.md) concernera une application pour gérer la maintenance des trottinettes.
Les derniers TP feront l'objet d'un assemblage des réalisation des différents morceaux de ce système.

