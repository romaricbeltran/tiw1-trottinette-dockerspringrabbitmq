# TP SPRING BOOT

## Prérequis

Aller dans le dossier /root/maintenance-web, et lancer le serveur de maintenance sur http://127.0.0.1:8080
```
mvn jetty:run
```

Lancer rabbitmq sur le port 5672 de docker
```
docker run -p 5672:5672 rabbitmq
```

Lancer keycloak (le fichier se trouve dans le dossier d'installation de keycloak /keycloak/bin) sur http://127.0.0.1:8180
```
./standalone.sh -Djboss.socket.binding.port-offset=100
```

Aller dans le dossier /root/banque et lancer la banque sur http://127.0.0.1:9090

```
mvn package && java -jar target/banque-0.0.1-SNAPSHOT.jar
```

Aller dans le dossier /root/SpringBootTP3 et lancer le systeme d'emprunt sur le serveur sur http://127.0.0.1:9000

```
mvn package && java -jar target/SpringBootTP3-0.0.1-SNAPSHOT.jar
```

## Requetes à exécuter

Avec jmeter d'abord, charger une liste de compte et d'autorisations dans la banque : /root/banque/test/jmeter-tests.jmx

#### Postman

##### Abonne
- Afficher la liste des abonnés : `GET sur 127.0.0.1:9000/abonne`
- Afficher un abonne d'id 3 : `GET sur 127.0.0.1:9000/abonne/3`
- Ajouter un abonne d'id 8 : `POST sur 127.0.0.1:9000/abonne/add/8`
- Supprimer l'abonne d'id 8 : `DELETE sur 127.0.0.1:9000/abonne/delete/8`

##### Emprunt
- Afficher la liste des emprunts (vide au début): `GET sur 127.0.0.1:9000/emprunt`
- Créer un emprunt de l'abonne 1 sur la trottinette 2 (la trottinette doit être disponible) : `POST sur 127.0.0.1:9000/emprunt/create/1/2`

La requête suivante déclenche l'envoi des informations d'emprunt à la banque pour qu'elle puisse créer une autorisation. La banque nous répondra par le numéro d'autorisation qui sera stocké dans emprunt.

- [rabbitMQ] Demande d'un numéro d'autorisation d'emprunt à la banque pour l'emprunt 11 depuis le compte 2 (on considère que le montant sera transféré au premier compte de la base de données celui du service de location) : 

`PUT sur 127.0.0.1:9000/emprunt/autorisation/11/2`

- [rabbitMQ] Envoie du numéro d'autorisation pour déclencher le transfert du montant de l'emprunt 11 du compte 2 à la banque et l'activation de la trottinette :

`PUT sur 127.0.0.1:9000/emprunt/send/11/2`

##### Trottinette

- Afficher la liste des trottinettes : `GET sur 127.0.0.1:9000/trottinette`
- Afficher une trottinette d'id 3 : `GET sur 127.0.0.1:9000/trottinette/3`
- Ajouter une trottinette : `POST sur 127.0.0.1:9000/trottinette/add`
- Supprimer une trottinette d'id 8 : `DELETE sur 127.0.0.1:9000/trottinette/delete/8`

## Partie Docker

Les dockers présentent des problèmes, nous conseillons de ne pas les utiliser pour tester l'application complète 

##### Lancer le docker maintenance-web (valide)
Se placer dans tiw-is-2019, il faut build le docker et binder le fichier de base de données maintenance (remplacer romaric par votre nom d'user)
```
docker build -t maintenance .
```
```
docker run -d --volume /home/romaric/maintenance-web.mv.db:/root/maintenance-web.mv.db -p 8080:8080 --name maintenance maintenance
```

### Lancer le docker banque (valide)
Se placer dans root/banque
```
mvn package
```
```
docker build -t banque .
```
```
docker run -d --volume /home/romaric/data/tiw1/banque.mv.db :/root/data/tiw1/banque.mv.db -p 9090:9090 --name banque banque
```

### Lancer le docker keycloak (potentiel problème de null pointer sur realm-export.json)
Après avoir exporté le realm keycloak depuis l'interface en local
```
docker run -p 8180:8180 -e KEYCLOAK_IMPORT=/tmp/realm-export.json --volume /home/romaric/realm-export.json:/tmp/realm-export.json -d --name keycloak jboss/keycloak
```

### Se connecter à l'administration Keycloak (créer les utilisateurs et rôles correspondant sur keycloak)

#### En tant qu'administrateur

###### romaric :
username : admin | mdp : tiw1

###### lucas :
username : lucas | mdp : p1408928
username : useradmin | mdp : userAdminTest

### En tant que l'utilisateur Romaric
 
###### romaric :
username : Romaric | mdp: testRomaric1

###### lucas :
username : user1 | mdp: user1Test

## Administration keycloak (http://127.0.0.1:8180/auth/)

username : admin | mdp : tiw1

## [Documentation Spring Fox](http://localhost:9000/swagger-ui.html)