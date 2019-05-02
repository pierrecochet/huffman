# Compression de données par codage de Huffman

Dans le cadre de nos études nous devions réaliser un programme de compression décompression de fichier.

**!!!!IMPORTANT!!!!**

Le programme fut écrit au travers de l'IDE IntelliJ il se peut donc qu'il y ait des problèmes de compatibilité dans le cas où vous importerez le dépôt dans d'autres IDE comme Eclipse pour n'en citer qu'un.
Si vous n'utilisez pas IntelliJ je vous invite donc à ne télécharger qu'uniquement les classes et le dossier data (pour les inputs/outputs) et à bien faire attention à adapter les chemins à partir desquels vous voulez générer les outputs (classe Huffman lignes : 82, 206, 329)

## Introduction
**Compression**

Cette partie fut décomposée en 3 étapes :
- Détermination de l’alphabet et des fréquences à partir du texte
- Construction de l’arbre de Huffman
- Codage du texte

**Décompression**

Il va de même pour cette partie :
- Determination de l'alphabet et des fréquences à partir d'un fichier de fréquences
- Construction de l'arbre de Huffman
- Décodage du texte 
