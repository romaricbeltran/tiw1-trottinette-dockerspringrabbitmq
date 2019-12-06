# TP6: Services SOAP et Message

Ce TP n'est pas à rendre mais sera utilisé pour le projet final de l'UE.

## Objectifs 

Utiliser et coder des services SOAP.
Implémenter une interaction asynchrone à travers un flux de messages. 

## Service bancaire et clients

Une nouvelle application est disponible dans le projet `root/banque`.
C'est une application Spring Boot exécutable via le jar généré ou directement via le plugin maven. 
Il s'agit d'un service bancaire en deux parties:

- une interface REST pour la gestion des comptes (classe `CompteController`) qui permet de:
  - créer des comptes
  - lister des comptes
  - créer des autorisations de transfert pour transférer un montant à partir d'un certain compte.
- un service SOAP (classe `TransfertEndpoint`) dont la description WSDL est disponible à l'adresse http://localhost:8080/ws/banque.wsdl une fois l'application démarrée. Ce service est utilisé pour effectuer des transferts.

### Clients REST & SOAP

Utiliser JMeter ou `curl` pour créer des comptes et des autorisations de transfert.

Installer [SOAPUI](https://www.soapui.org/downloads/soapui.html), puis créer un nouveau projet SOAP en indiquant l'URL mentionnée précédement pour le WSDL. Demander directement à créer une _TestSuite_.
Remplir les données du message d'entrée et vérifier le bon fonctionnement du service de transfert.

> Il est également possible d'utiliser SOAPUI pour effectuer des requêtes REST. 

## Implementation de service SOAP via Spring 

Reprendre l'application Spring du [TP3](../tp3/README.md).
On souhaite ajouter un service SOAP à cette application afin de confirmer qu'une réservation de trottinette est possible.
Ce service exposera une opération qui active une trottinette via un numéro d'activation.
L'idée est que l'activation d'une trottinette lors d'un emprunt ne se fera que lorsque qu'un transfert bancaire aura été confirmé.

Ajouter les dépendances suivantes: 
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web-services</artifactId>
</dependency>

<dependency>
    <groupId>wsdl4j</groupId>
    <artifactId>wsdl4j</artifactId>
</dependency>
```

> Bien vérifier que le parent de votre projet est `spring-boot-starter-parent`:
> 
> ```xml
> <parent>
> 	<groupId>org.springframework.boot</groupId>
> 	<artifactId>spring-boot-starter-parent</artifactId>
> 	<version>2.2.1.RELEASE</version>
> 	<relativePath/> <!-- lookup parent from repository -->
> </parent>
> ```
> 
> C'est le cas si vous aviez bien généré le projet de base via https://start.spring.io/

Modifier le comportement des emprunts: un emprunt peut être activé ou non et il n'est pas actif à la création et tant que le transfert n'a pas été confirmé.

Créer un fichier `confirmation.xsd` qui contient les définition XmlSchema pour l'opération d'activation via un numéro d'activation. Configurer le plugin `jaxb2-maven-plugin` pour générer le code Java correspondant (_c.f._ le projet `banque`).

Créer le service SOAP en vous inspirant de ce qui est fait dans le projet `banque`.

Tester avec SOAPUI

## Transmission de références

Modifier le projet `banque` de façon à:

- ajouter une référence à un numéro d'activation dans les paramètres de transfert
- ajouter une URL de service de confirmation/activation dans les paramètres de transfert
- ajouter un appel à ce service si le transfert s'est bien effectué

Modifier l'application du TP3 de façon à:
- renvoyer un numéro d'activation, un montant et l'URL du service de confirmation lors de la création d'un emprunt

## Client Java

Pour terminer, créer un client Java en ligne de commande qui simulera un client mobile via les interactions suivantes sur les APIs SOAP et REST `tp3`et `banque`:

- demande de création d'un emprunt sur une trottinette 
- création d'une autorisation de transfert
- demande de transfert
- vérification de déblocage de la trottinette (_i.e._ emprunt activé) à intervalles réguliers jusqu'à ce que la trottinette soit effectivement activée

## Analyse et correction

Faire un diagramme de séquence des interactions lors de l'emprunt d'une trottinette.
Identifier les différents problèmes de sécurité résultant cette interaction et proposer une modification du diagramme pour y remédier lorsque cela est possible. Implémenter et tester.

## Transmission via RabbitMQ

On souhaite fiabiliser la communication banque ↔ emprunts de trottinettes via un système de transmission de messages dédié. 

On utilisera [RabbitMQ](https://www.rabbitmq.com/) comme _broker_. 
Il est déployable simplement via [un conteneur Docker](https://hub.docker.com/_/rabbitmq) (utiliser avec le tag `management` pour pouvoir visualiser les messages transmis).

Modifier le comportement des applications emprun et banque:

- Ajouter [Spring AMQP](https://spring.io/projects/spring-amqp) aux projet emprunt et banque:

  ```xml    
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.amqp</groupId>
    <artifactId>spring-rabbit-test</artifactId>
    <scope>test</scope>
  </dependency>
  ```

  et modifier la dépendance vers Spring Boot Test:

  ```xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
    <exclusions>
      <exclusion>
        <groupId>org.junit.vintage</groupId>
        <artifactId>junit-vintage-engine</artifactId>
      </exclusion>
    </exclusions>
  </dependency>
  ```
  
- Ajouter un consommateur de messages dans emprunt via un bean annoté [doc](https://docs.spring.io/spring-amqp/docs/2.2.2.RELEASE/reference/html/#async-annotation-driven)
- Changer ensuite le comportement de la banque pour qu'elle envoie le message de confirmation de transfert via RabbitMQ.
  L'adresse de la file RabbitMQ à utiliser sera transmise via le message SOAP de demande de transfert.
