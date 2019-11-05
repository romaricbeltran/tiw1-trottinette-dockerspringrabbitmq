Université Claude Bernard Lyon 1 – M2 TIW – Intergiciels et Services

# TP 3 : Spring

N'oubliez pas de faire une branche git `tp3` dans laquelle vous ferez toutes les modifications de ce TP.
Si vous travaillez à deux, créez bien ce projet dans un groupe gitlab et faites chacun un fork.

## Objectifs

Ce TP a pour objectif de coder une application en utilisant un _framework_ complet, à savoir [Spring](https://spring.io).

L'application à coder est celle du [TP2](../tp2/README.md), mais avec un server Web exposant une interface REST et enrichie avec une gestion de la sécurité.

## Spring Boot 

(Appli spring MVC + Spring Data + OpenAPI + JMeter)

### Initialisation du projet

Au lieu de modifier l'application du TP2, on partira d'une nouvelle application Spring Boot initalisée via https://start.spring.io/.

On y intégrera les modules suivants: Web, Security, Data JPA


### Contexte simple et mise en place des tests

Créer un composant simple (qui ne fait pas appel à de la persistence des données), par exemple le composant qui récupère les informations sur les trottinettes.
On hésitera pas à reprendre une partie du code du TP2 pour implémenter ce composant.

Mettre en place des tests mettant en oeuvre le composant en l'injectant dans la classe de test générée par SpringBoot.

**Remarque:** l'injection dans une classe de test se fait comme l'injection dans un composant classique Spring.

### Data

Implémenter le reste des fonctionalités décrites dans le TP2.
Pour cela créera des composants en prenant le parti de tout gérer en base de données via JPA.

Tester en java (test unitaires et/ou tests d'intégration avec l'_ApplicationContext_ Spring fourni via `@SpringBootTest`).

### API Rest

Mettre en oeuvre une API REST pour votre application en utilisant des composants `@RestController` Spring.

Décrire cette API via un fichier [OpenAPI](https://swagger.io/specification/). 
Ce fichier pourra être écrit à la main ou généré via [SpringFox](http://springfox.github.io/springfox/docs/current/).

EC: Vérifier le fonctionnement ci-dessous:
> Utiliser le générateur de test jmeter à partir de ce fichier via [Swagger Codegen](https://swagger.io/tools/swagger-codegen/). À noter qu'il faut compiler swagger-codegen avec java 8 (le jar se trouve ensuite dans `modules/swagger-codegen-cli/target`)
> 
> *Remarque:* Du point de vue de Swagger Codegen, JMeter est un client pour votre API. 


## Sécuriser avec Spring Security

À venir

(e.g. authentification avec OAuth)
(penser à Keycloak)
(réfléchir à des rôles, par exemple un admin et des niveau d'utilisateur (silver/gold, etc sur e.g. des temps d'utilisation de trottinette))
(ref https://www.baeldung.com/spring-boot-keycloak)

### Spring AOP

À venir

## Divers à éventuellement intégrer

(Use case logs: composant de facturation fourni, mais buggé à intégrer, puis débugger avec les logs)
(OpenAPI en YAML, peut permettre de générer des tests JMeter, utiliser http://springfox.github.io/springfox/ ?)
