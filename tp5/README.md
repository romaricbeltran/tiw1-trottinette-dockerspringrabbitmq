Université Claude Bernard Lyon 1 – M2 TIW – Intergiciels et Services

# TP OSGi

## Objectifs pédagogiques

Expérimenter une architecture à base de composants téléchargeables dynamiquement.

## Outils

Dans ce TP, vous utiliserez le framework [Felix](http://felix.apache.org/documentation/subprojects/apache-felix-ipojo.html), qui contient une implémentation minimale d'OSGi et une console ("gogo" shell) en ligne de commande.
Un début de documentation de ce shell est disponible [ici](https://felix.apache.org/documentation/subprojects/apache-felix-framework/apache-felix-framework-usage-documentation.html#framework-shell).

## Découverte du framework Felix

Téléchargez et décompressez le framework. Vous pouvez ouvrir un shell en exécutant, depuis le répertoire principal du framework, la commande `java -jar bin/felix.jar`.

Utiliser la documentation située [ici](https://felix.apache.org/documentation/subprojects/apache-felix-framework/apache-felix-framework-usage-documentation.html#framework-shell) pour démarrer. Par exemple, taper `lb` (list bundles) pour avoir la liste des bundles installés par défaut.

### Chargement et exécution d'un bundle

Explorer le [Repository Felix OBR](http://felix.apache.org/downloads.cgi). Choisir et télécharger un ou plusieurs bundle(s), installer, lancer et stopper ce(s) bundle(s) comme indiqué [là](http://felix.apache.org/site/apache-felix-framework-usage-documentation.html#ApacheFelixFrameworkUsageDocumentation-installingbundles). Normalement, vous devez voir s'exécuter les méthodes de gestion du cycle de vie `start()` et `stop()`, d'implémentation de l'interface `BundleActivator`.

Dans la suite, vous pourrez avoir besoin de recourir à ce repo pour récupérer et déployer des bundles nécessaires à l'exécution de votre application.

### Réalisation d'un bundle avec iPOJO

Vous allez maintenant réaliser vous-même un bundle OSGi, en suivant le tutoriel situé [ici](https://felix.apache.org/documentation/subprojects/apache-felix-ipojo/apache-felix-ipojo-gettingstarted/ipojo-hello-word-maven-based-tutorial.html).

- Une fois les fichiers recopiés et les jars faits, suivre la même procédure que précédemment pour les déployer et les démarrer.
  Vous devrez démarrer les bundles suivants (dans "annotations/hello.felix.annotations/target/bundle") : `org.apache.felix.ipojo-1.12.1`, `hello.service-1.12.1`, `hello.impl.annotations-1.12.1` et `hello.client.annotations-1.12.1`.
- Alternativement, partez du code téléchargeable au début du tuto, puis suivez les instructions pour compiler avec Maven et lancer félix dans le répertoire `annotations/hello.felix.annotations/target`.

## Reprise de l'application de gestion de trottinettes

En suivant l'exemple précédent, créez un bundle OSGI capable de récupérer des trottinettes en HTTP depuis votre application de maintenance.

Ce bundle dépendra du module [HttpClient d'Apache](https://hc.apache.org/httpcomponents-client-ga/) :

- [Documentation du bundle](https://hc.apache.org/httpcomponents-client-ga/httpclient-osgi/apidocs/)
- [Dépendance Maven](https://hc.apache.org/httpcomponents-client-ga/httpclient-osgi/dependency-info.html)