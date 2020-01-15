# TP orchestration avec Kubernetes

## Introduction

### Objectifs

Ce TP vise à expérimenter l'utilisation d'un orchestrateur de conteneurs, ici [Kubernetes](https://kubernetes.io/):
- déploiement de conteneurs et des ressources associées
- élasticité
- reconfiguration et redéploiement

### Organisation

Les étudiants devront se consituer en binômes (appelés groupes par la suite). 
Ces binômes seront utilisés pour le projet final de l'UE.
Les binôme sont à déclarer auprès d'Emmanuel Coquery qui attribura un numéro de groupe à chaque binôme.

### Infrastructure

Un cluster Kubernetes (`tiw1`) est mis à disposition.
Chaque étudiant recevra un mot de passe via tomuss (le login est le même que le compte étudiant usuel).
Une interface Web de gestion (instance [Rancher](https://rancher.com/)) est disponible à l'adresse https://192.168.237.144.


Bien que cette interface permette d'effectuer un certain nombre d'actions, il est demandé d'utiliser plutôt l'interface en ligne de commandes de Kubernetes: [`kubectl`](https://kubernetes.io/docs/tasks/tools/install-kubectl/).
Cet utilitaire nécessite une configuration pour accéder au cluster.
Celle-ci peut être récupérée en cliquant sur "Cluster: tiw1" dans le menu déroulant en haut à gauche, puis dans le Dashboard (accessible via l'onglet Cluster à côté du menu déroulant) et enfin le bouton Kubeconfig file.

Chaque groupe s'est vu associé un projet au sein du cluster.
Dans ce projet, un *namespace* de la forme `grp-xx` a été créé.
La commande suivante va permettre de changer la configuration par défaut de `kubectl` pour changer utiliser ce namespace par défaut (ne pas oublier de changer `xx`):

```shell
kubectl config set-context $(kubectl config current-context) --namespace=grp-xx
```

> Il faudra bien penser à créer toutes vos ressources dans ce namespace.

Pour tester que kubectl fonctionne, lancer la commande suivante:

```shell
kubectl get pod
```

Cette commande devrait répondre: "No resources found."

## Premiers déploiements

Il est conseillé de bien garder les fichiers utilisés pour cette partie: il pourront servir de point de départ pour le déploiement de l'application de gestion des trottinettes.

Dans la suite du TP, bien penser à toujours ajouter votre namespace (`grp-xx`) comme champ `namespace` dans la section [`metadata`](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.13/#objectmeta-v1-meta) de vos fichiers de déploiements yaml.

### Conteneurs stateless: nginx

Suivre [ce tutoriel](https://kubernetes.io/docs/tasks/run-application/run-stateless-application-deployment/) afin de déployer un conteneur `nginx`.

> Avant de détruire le déploiement à la fin du tutoriel, vérifiez que le conteneur fonctionne correctement via [`kubectl port-forward`](https://kubernetes.io/docs/tasks/access-application-cluster/port-forward-access-application-cluster/#forward-a-local-port-to-a-port-on-the-pod).

### Conteneurs stateful: postgresql

Adapter le [tutoriel sur le déploiement de MySQL](https://kubernetes.io/docs/tasks/run-application/run-single-instance-stateful-application/) pour:

- utiliser une base PostgreSQL ([image](https://hub.docker.com/_/postgres))
  - attention au point de montage et aux variables d'environnement
- utiliser un volume dont la taille est limitée à 1 Gi
- ne pas créer de `PersistentVolume`, mais simplement un [`PersistentVolumeClaim`](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.13/#persistentvolumeclaim-v1-core) utilisant:
  - `sc-n2m4n` comme `storageClassName`
- bien penser à préciser la valeur de la variable `PGDATA` comme indiqué dans [la documentation de l'image docker](https://hub.docker.com/_/postgres).

## Déploiement de l'application Maintenance

Avant de commencer cette partie, il faut avoir terminé la partie "Mise en place de l'application" du [tp7](../tp7/README.md).
Il faut également avoir poussé les images sur le registry de votre projet forge.

### Projet maintenance-web

Commencer par déployer l'application `maintenance-web`.
Le déploiement est proche du conteneur nginx précédent, mais:

- Il faut prévoir en plus un *service* kubernetes comme dans l'exemple de la base de données.
- L'image sera tirée depuis la forge, il faut donc suivre [les instructions pour l'utilisation d'un registry privé](https://kubernetes.io/docs/tasks/configure-pod-container/pull-image-private-registry/). 
  Bien penser à créer une clé d'API dans gitlab pour éviter d'exposer votre mot de passe personnel.

Si vous utilisez une base de donnée externe au conteneur de maintenance, effectuez également la partie suivante afin de disposer d'une base opérationnelle.
Tester que l'application répond correctement via `kubectl port-forward`.
Dans ce cadre, essayer d'accéder au service plutôt qu'au pod.

### Base de données

Reprendre le déploiement de la base de données, mais en utilisant un *ConfigMap* pour fixer le nom de la base, l'utilisateur et le mot de passe.

### Reverse proxy nginx

Déployer un nginx qui servira de reverse proxy pour les différentes parties de l'application de gestion des trottinettes et pour les services accédés par ce dernier.
Cela consiste à adapter la configuration adoptée dans le cadre du [tp7](../tp7/README.md) et à l'injecter dans le conteneur déployé via un *ConfigMap*, [voir comment faire ici](https://kubernetes.io/docs/tasks/configure-pod-container/configure-pod-configmap/).
Penser que les services cachés derrière le reverse proxy sont addressable via le [DNS interne de Kubernetes](https://kubernetes.io/docs/concepts/services-networking/dns-pod-service/).

Tester à nouveau via `kubectl port-forward`.

Si ce n'est déjà fait, déployer un *Service* pour masquer le *Replica Set* contenant nginx.
Vous avez également la possibilité d'utiliser ce service pour rendre nginx visible depuis l'extérieur de kubernetes, mais les restrictions réseau d'accès au cluster rendent cela peu intéressant pour ce TP.


### Autres services

Déployer chacun des autres services (banque, emprunt, rabbitmq, *etc*), en reconfigurant le reverse proxy au besoin.
Tester que l'application ainsi déployée est fonctionnelle à travers l'interface des tests jmeter en y accédant via `kubectl port-forward`.
Vous pouvez aussi tester vos services avec *e.g.* SoapUI.

[Ajouter des *health check*](https://kubernetes.io/docs/tasks/configure-pod-container/configure-liveness-readiness-probes/) pour le bon fonctionnement (*readyness*) et le témoin de vie (*liveness*) de vos pods.

Tester en détruisant à la main un pod, puis en tuant à la main le processus dans un conteneur via `kubectl exec`.

## Passage à l'échelle

Pour cette partie, afin de simuler plus facilement une charge importante, assurez vous de [limiter la capacité CPU](https://kubernetes.io/docs/tasks/configure-pod-container/assign-cpu-resource/) et éventuellement [RAM](https://kubernetes.io/docs/tasks/configure-pod-container/assign-memory-resource/) de vos conteneurs, par exemple à `100m` CPU.

### Job de benchmark

À venir

### Essai de plusieurs configurations

À venir

## Points techniques

### DNS

Un DNS interne est installé dans le cluster Kubernetes.
Au sein d'un namespace, un *Service* est accessible avec comme nom de domaine le nom du *Service*.
Le nom complet du service est en fait plus long (voir la [doc](https://kubernetes.io/docs/concepts/services-networking/dns-pod-service/#services)). 

### Pull d'une image stockée sur la forge depuis Kubernetes

1. Créer une clé d'API dans la forge: dans gitlab, aller dans votre menu utilisateur -> settings. 
   À gauche, cliquer sur Access Tokens et créer un nouveau token avec *read_registry* comme scope.
2. Tester que le mot de passe fonctionne correctement avec la commande `docker login forge.univ-lyon1.fr:4567 -u p1234567890 -p abcdefghijkl` où *p1234567890* est votre login UCBL et  *abcdefghijkl* est le token créé dans gitlab.
3. Créer un secret Kubernetes pour le pull d'image ([doc](https://kubernetes.io/docs/tasks/configure-pod-container/pull-image-private-registry/#create-a-secret-in-the-cluster-that-holds-your-authorization-token)): 
   `kubectl --namespace ns-xx create secret docker-registry p1234567890-forge --docker-server=forge.univ-lyon1.fr:4567 --docker-username=p1234567890 --docker-password=abcdefghijkl --docker-email=mon.mail@univ-lyon1.fr` (penser à bien adapter la commande).
   *p1234567890-forge* est le nom du secret (vous pouvez changer pour prendre autre chose de plus parlant si vous voulez).
4. Dans votre *Deployment*, dans la spec du pod au même niveau que `containers`, ajouter une entrée `imagePullSecrets` comme indiqué dans la [doc](https://kubernetes.io/docs/tasks/configure-pod-container/pull-image-private-registry/#create-a-pod-that-uses-your-secret) (attention l'exemple est pour un *Pod*, pas un *Deployement*, il faut faire attention à l'endroit où on place `imagePullSecrets`). Remplacer `regcred` par *p1234567890-forge*.

Seule la dernière étape est à répéter pour les différents *Deployments*, les 3 premières sont à faire juste une fois.

### Construire et stocker une image docker sur la forge via Gitlab-CI

Il est possible de construire une image docker en intégration continue sur la forge.
Ci-dessous un exemple de job à placer dans le fichier .gitlab-ci.yml:

```yaml
docker-image-build:
  stage: build
  image: docker:latest
  variables:
    ALL_PROXY: ""
    all_proxy: ""
    IMAGE_TAG: forge.univ-lyon1.fr:4567/*LOGIN ETU P1XXXXXX*/*NOM PROJET FORGE*/*NOM DE L'IMAGE*:latest  #voir ci-dessous
  services:
    - docker:dind
  tags:
    - docker_ci_build
  script:
    - docker login -u gitlab-ci-token -p $CI_JOB_TOKEN $CI_REGISTRY
    - docker build --build-arg http_proxy="http://proxy.univ-lyon1.fr:3128" -t $IMAGE_TAG *REPERTOIRE AVEC LE DOCKERFILE* #utiliser . avec le Dokerfile a la racine du projet
    - docker push $IMAGE_TAG
```

Remarques: 

- la variable `IMAGE_TAG` est à adapter en fonction du projet, le début peut être obtenu en allant sur la page de votre projet forge et en cliquant sur *Registry* à gauche.
- les variables `$CI_JOB_TOKEN` et `$CI_REGISTRY` sont gérée automatiquement par Gitlab.
- le switch `--build-arg http_proxy="http://proxy.univ-lyon1.fr:3128"` permet d'injecter la variable `http_proxy` dans l'environement utilisé pour le docker build.
  Il sera peut-être nécessaire de faire d'autre adaptation à votre Dockerfile à cause du proxy
- Utiliser les images maven alpine : `3.6.0-jdk-8-alpine`, aussi bien pour l'intégration que pour votre image, cela evite le bug `org.apache.maven.surefire.booter.SurefireBooterForkException: The forked VM terminated without properly saying goodbye.`
- Ajouter une éxécution du script de configaration du proxy maven `RUN sh config/maven/setup-proxy-settings.sh` dans votre dockerfile pour régler les problemes de proxy
- Un problème d'authentification empêche pour le moment l'utilisation de ces images au sein de l'intégration continue de gitlab.
