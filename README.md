# Compression de données par codage de Huffman

Dans le cadre de nos études nous devions réaliser un programme de compression décompression de fichier.

**!!!!IMPORTANT!!!!**

Le programme a été écrit au travers de l'IDE IntelliJ il se peut donc qu'il y ait des problèmes de compatibilité dans le cas où vous importerez le dépôt dans d'autres IDE comme Eclipse pour n'en citer qu'un.
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

## Tester le code

```
public class Main {
    public static void main(String[] args)throws IOException {
        Huffman huff = new Huffman();
        //zip
        File file = new File("src/data/test.txt");
        huff.zip(file);
        //unzip
        File f1 = new File("src/data/compressedFile.txt");
        File freqFile = new File("src/data/freq.txt");

        huff.unzipFile(f1,freqFile);
    }
```
Tout ce passe dans la classe Main pour tester le code.

Pour compresser un fichier il suffit de rentrer le chemin pointant sur votre fichier en paramètre du premier new File()

Pour décompresser un fichier il faut de rentrer le chemin pointant sur votre fichier de fréquences en paramètre du deuxièmes new File(), puis de votre fichier à décompresser dans le troisième new File().

## Contact :

Si vous rencontrez un quelconque soucis quant à l'importation du dépôt ou son utilisation vous pouvez me contacter ici :

