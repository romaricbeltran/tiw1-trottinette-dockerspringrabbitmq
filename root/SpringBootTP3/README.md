# TP SPRING BOOT

## Prérequis

Dans le dossier maintenance-web, pour lancer le serveur de maintenance sur le port 8080
```
mvn jetty:run
```

Lancer keycloak (le fichier se trouve dans le dossier d'installation de keycloak /keycloak/bin)
```
./standalone.sh -Djboss.socket.binding.port-offset=100
```

## Pour compiler et lancer le serveur

```
mvn package && java -jar target/SpringBootTP3-0.0.1-SNAPSHOT.jar
```
## Se connecter

### En tant qu'administrateur

username : admin | mdp : tiw1

### En tant que l'utilisateur Romaric
 
username : Romaric | mdp: testRomaric1

## [Documentation Spring Fox](http://localhost:9000/swagger-ui.html)