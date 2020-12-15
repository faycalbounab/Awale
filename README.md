# Awale

Projet d'IA pour le Master 1 Informatique et Interactions à L'Université Côte d'Azur en 2018/2019.
Objectif : Implémenter une nouvelle version pour le jeu de l'Awalé afin de faire une compétition avec nos camarades de classe. L'Awalé est un jeu de société combinatoire abstrait créé en Afrique.

## Règles

Il y a 12 trous, 6 par joueur. Le premier joueur est au sommet. Les trous sont numérotés de 1 à 6 pour le premier joueur et de 7 à 12 pour le second. Le trou 7 suit le trou 6 dans le sens des aiguilles d'une montre. Le trou 1 suit le trou 12 dans le sens des aiguilles d'une montre.
Il y a 2 couleurs: rouge et noir, au début, il y a 3 graines de chaque couleur par trou. Chaque joueur a une graine spéciale, qui est rouge et noire.

### Objet

Le jeu commence avec 3 + 3 graines dans chaque trou et une graine spéciale mise dans un autre trou (le premier joueur décide de sa position en premier, puis le second). Le but du jeu est de capturer plus de graines que son adversaire. Puisqu'il y a un nombre pair de graines, il est possible que la partie se termine par un match nul, où chaque joueur en a capturé 38.

### Semer

Les joueurs déplacent les graines à tour de rôle. Lors d'un tour, un joueur choisit l'un des six trous sous son contrôle. Le joueur enlève toutes les graines de ce trou et les distribue, en en déposant une dans chaque trou dans le sens des aiguilles d'une montre, selon un processus appelé semer. Les graines ne sont pas distribuées dans les trous d'extrémité, ni dans le trou tiré de. Le trou de départ est toujours laissé vide; s'il contient 12 graines (ou plus), il est sauté et la douzième graine est placée dans la maison voisine.
Les mouvements sont faits selon les couleurs. D'abord une couleur est conçue et toutes les graines de cette couleur sont jouées, alors les graines de l'autre couleur sont jouées. S'il y a des graines spéciales, alors elles sont jouées à partir d'une position donnée.
Ainsi, un mouvement est exprimé par NCS où N est le numéro du trou, C est la première couleur jouée, S est la première position de la graine. Par exemple, 3R2 signifie que nous jouons les graines du trou 3, en jouant d’abord le rouge

### Capture

Dans Awale Abapa, la capture n'a lieu que lorsqu'un joueur porte le nombre de trous d'un adversaire à exactement deux ou trois graines colorées de la même couleur que la graine finale. Cela capture toujours les graines dans le trou correspondant, et peut-être même plus: si la graine avant-dernière amène également le trou d'un adversaire à deux ou trois graines de la même couleur que la graine finale, celles-ci sont également capturées, etc. jusqu'à ce qu'un trou soit atteint qui ne contient pas deux ou trois graines de la même couleur de la graine finale ou n'appartient pas à l'adversaire. Les semences spéciales sont considérées à la fois colorées pour capturer les semences ou pour être capturées. Les graines capturées sont mises de côté. Affamer l'adversaire est interdit.
Prendre toutes les graines de l'adversaire est autorisé. En cas de famine, toutes les graines sont capturées par le dernier joueur.
Quand il n'y a plus de coup valide, le jeu s'arrête et chaque joueur prend les graines de son côté.

### Gagnant

Le jeu est terminé lorsqu'un joueur a capturé 38 graines ou plus, ou chaque joueur a pris 37 graines (tirage au sort). Si les deux joueurs s'accordent pour dire que le jeu a été réduit à un cycle sans fin, le jeu se termine lorsque chaque joueur a des graines dans ses trous, puis chaque joueur capture les graines de son côté du tableau

## Les fonctions

Les différentes fonctions de notre code Java.

### famine

``` public static boolean famine(Position pos_current, boolean computer_play){} ```

Cette fonction renvoi un boolean pour la famine.

### validMove

```public static boolean validMove(Position pos, boolean computer_play, int i){}```

Cette fonction renvoi un boolean si l'action du joueur est valide ou non.

### finalPosition

```public static boolean finalPosition(Position pos,boolean computerplay){}```

Cette fonction renvoi un boolean qui indique si la victoire d'un des deux joueurs est impossible(l'un des joueurs a ramassé plus de la moitié des graines sur le terrain).

### evaluation

```public static int evaluation(Position pos){}```

Cette fonction renvoi un entier qui est la différence des graines prises.

### playMove

```public static void playMove(Position posNext,Position pos,boolean computer_play,int i,boolean noir,int posSeed){}```
Cette fonction joue un coup, elle prends les graines et les ditribue et elle ramasse les graines si elle est autorisée.

### minMaxValue

```public static int minMaxValue(Position pos_current,boolean computer_play, int depth,int depthMax,int alpha,int beta){}``

Cette fonction est un algorithme qui trouve le chemin qui rapporte le plus de point?

### startGame

```public static void startGame(Position pos_current,boolean computer_play){}```

Cette fonction initialise les différents élément de la partie.

### affichage

```public static void affichage(Position position){}```

Cette fonction permet d'afficher le plateau du jeu.