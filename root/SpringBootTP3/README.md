# TP SPRING BOOT

## Prérequis

Dans le dossier maintenance-web, pour lancer le serveur de maintenance sur http://127.0.0.1:8080
```
mvn jetty:run
```

Lancer rabbitmq sur le port 5672
```
docker run -p 5672:5672 rabbitmq
```

Lancer keycloak (le fichier se trouve dans le dossier d'installation de keycloak /keycloak/bin) sur http://127.0.0.1:8180
```
./standalone.sh -Djboss.socket.binding.port-offset=100
```

## Pour compiler et lancer le serveur sur http://127.0.0.1:9000

```
mvn package && java -jar target/SpringBootTP3-0.0.1-SNAPSHOT.jar
```

## Se connecter (créer les utilisateurs et rôles correspondant sur keycloak)

### En tant qu'administrateur

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