# Partie 2 – Gestion des relations

## 1 – Gestion de relation entre Project et Enterprise

Chaque projet est  proposé par une entreprise qui doit être spécifiée à la création d'un projet.

1. Modifiez le contenu du fichier ProjectTest.java pour qu'il soit identique au contenu disponible ici : 
https://gist.github.com/FranckSilvestre/70ca9d583d59cae9411522346c547482 

2. Lancez les tests de la classe ProjectTest et constatez que certains tests ne passent plus.

3. Modifiez le contenu de la classe Project  pour que les tests passent de nouveau.

> fix #2.1 relation Project Enterprise

## 2 – Gestion de relation entre Enterprise et Project

1. Modifiez le contenu du fichier EnterpriseTest.java pour qu'il soit identique au contenu disponible ici :
https://gist.github.com/FranckSilvestre/ada676544b751a081f8e4f5f67a29e70 

2. Lancez les tests de la classe EnterpriseTest et constatez que certains tests ne passent plus.

3. Modifiez le contenu de la classe Enterprise pour expliciter la relation one-to-many de Enterprise vers Project de telle sorte que les tests passent de nouveau.

>  fix #2.2 relation Enterprise Project 

## 3. Amélioration du service

1. Modifiez le contenu du fichier EnterpriseProjectServiceIntegrationTest.java pour qu'il soit identique au contenu disponible ici :
https://gist.github.com/FranckSilvestre/d6c39fa70b5ad64c6cf98022fc103193 

2. Lancez les tests et constatez que certains tests ne passent plus.

3. Modifiez le contenu des classes Project, Enterprise et EnterpriseProjectService de telle sorte que les tests de la classe EnterpriseProjectServiceIntegrationTest passent de nouveau.

> fix #2.3 service improvement

## Tests de non régression

4. Lancez l'ensemble des tests et vérifiez que vous n'obtenez pas de régression. En cas de régression observée, modifiez la ou les classes nécessaires pour que l'ensemble des tests passent de nouveau.

> fix #2.4 non regression OK

La prochaine partie vise la mise en place un Web service exposant la liste des projets disponibles avec leur entreprise.