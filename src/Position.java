import java.util.Scanner;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Position {

    private boolean computerplay;
    private int cell_players[][] = new int[12][3];;
    private int seed_player;
    private int seed_computer;
    public static int INDICE, POSSEED;
    public static boolean COULEUR,FIRSTPLAYER;
    public static int MIN = -1000;
    public static int MAX = 1000;


    //////////////////////////////GETTER AND SETTER//////////////////////////////

    public Position() {
    }

    public boolean isComputerplay() {
        return computerplay;
    }

    public void setComputerplay(boolean computerplay) {
        this.computerplay = computerplay;
    }

    public int[][] getCell_players() {
        return cell_players;
    }

    public void setCell_players(int[][] cell_players) {
        this.cell_players = cell_players;
    }

    public void setCells_players(int i, int j, int val){this.cell_players[i][j] = val;}

    public int getSeed_player() {
        return seed_player;
    }

    public void setSeed_player(int seed_player) {
        this.seed_player = seed_player;
    }

    public int getSeed_computer() {
        return seed_computer;
    }

    public void setSeed_computer(int seed_computer) {
        this.seed_computer = seed_computer;
    }

    //////////////////////////////Méthodes//////////////////////////////

    public static void actualiseVal(int indice,int posSeed,boolean noir,int depth){
        if(depth == 0){

            if(FIRSTPLAYER){
                System.out.print(indice + 1);
            }
            else{
                System.out.print(indice + 7);
            }

            if(noir){
                System.out.print("N");
            }
            else{
                System.out.print("R");
            }

            System.out.println(posSeed);
            INDICE = indice;
            COULEUR = noir;
            POSSEED = posSeed;
        }
    }

    //****************************** FAMINE POUR VALIDMOVE ET FINALPOSITION ****************************//

    public static boolean finalPosition(Position pos,boolean computerplay){
        if(pos.getSeed_player() > 37 || pos.getSeed_computer() > 37){
            return true;
        }
        if(famine(pos,computerplay)){
            return true;

        }


        return false;
// if plus de seed ou pas de coup jouable ou tas plus de la moitier des graines
    }

    public static boolean famine(Position pos_current, boolean computer_play){ // LA FAMINE
        if(!computer_play){
            if(pos_current.getCell_players()[6][0] + pos_current.getCell_players()[6][1] + pos_current.getCell_players()[6][2]  +
                    pos_current.getCell_players()[7][0] + pos_current.getCell_players()[7][1] + pos_current.getCell_players()[7][2]  +
                    pos_current.getCell_players()[8][0] + pos_current.getCell_players()[8][1] + pos_current.getCell_players()[8][2] +
                    pos_current.getCell_players()[9][0] + pos_current.getCell_players()[9][1] + pos_current.getCell_players()[9][2] +
                    pos_current.getCell_players()[10][0] + pos_current.getCell_players()[10][1] + pos_current.getCell_players()[10][2] +
                    pos_current.getCell_players()[11][0] + pos_current.getCell_players()[11][1] + pos_current.getCell_players()[11][2] == 0) {
                return true;
            }
        }
        if(computer_play){
            if(pos_current.getCell_players()[0][0] + pos_current.getCell_players()[0][1] + pos_current.getCell_players()[0][2] +
                    pos_current.getCell_players()[1][0] + pos_current.getCell_players()[1][1] + pos_current.getCell_players()[1][2] +
                    pos_current.getCell_players()[2][0] + pos_current.getCell_players()[2][1] + pos_current.getCell_players()[2][2] +
                    pos_current.getCell_players()[3][0] + pos_current.getCell_players()[3][1] + pos_current.getCell_players()[3][2] +
                    pos_current.getCell_players()[4][0] + pos_current.getCell_players()[4][1] + pos_current.getCell_players()[4][2] +
                    pos_current.getCell_players()[5][0] + pos_current.getCell_players()[5][1] + pos_current.getCell_players()[5][2] == 0) {

                return true;
            }
        }
        return false;
    }

    public static boolean validMove(Position pos, boolean computer_play, int i){

        if (computer_play && i >= 0 && i < 6 && (pos.getCell_players()[i][0] + pos.getCell_players()[i][1] + pos.getCell_players()[i][2]) > 0) {
            return true;
        }
        if (!computer_play && i >= 6 && i < 12 && (pos.getCell_players()[i][0] + pos.getCell_players()[i][1] + pos.getCell_players()[i][2]) > 0) {
            return true;
        }

        return false;
    }



    public static int evaluation(Position pos){
        return pos.getSeed_computer() - pos.getSeed_player();
    }


    public static void playMove(Position posNext,Position pos, boolean computer_play, int i, boolean noir, int posSeed) {
        int nbNoir = pos.getCell_players()[i][0];
        int nbRouge = pos.getCell_players()[i][1];
        int nbSpe = pos.getCell_players()[i][2];
        int nb = pos.getCell_players()[i][0] + posNext.getCell_players()[i][1] + posNext.getCell_players()[i][2];
        int tour = 1;
        int incrPos = 0;
        int lastSeed = -1;

        //copie de pos

        posNext.setSeed_player(pos.getSeed_player());
        posNext.setSeed_computer(pos.getSeed_computer());
        for (int k = 0; k < 12; k++) {
            for (int j = 0; j < 3; j++) {
                posNext.setCells_players(k,j,pos.getCell_players()[k][j]);
            }
        }

        //Distribution des graines

        if(posSeed > 0) {//si il faut distribuer une speciale
            while(tour <= nb) {
                if ((tour + incrPos)% 12 == 0) {
                    incrPos++;
                }
                if (posSeed == tour) {
                    if (nbSpe == 1) {
                        posNext.getCell_players()[(i + incrPos + tour) % 12][2]++;
                        nbSpe--;
                        lastSeed = 2;
                    } else {
                        posNext.getCell_players()[(i + incrPos + tour) % 12][2]++;
                        nbSpe--;
                        lastSeed = 2;
                        tour++;
                        if ((tour + incrPos) % 12 == 0) {
                            incrPos++;
                        }
                        posNext.getCell_players()[(i + incrPos + tour) % 12][2]++;
                        nbSpe--;
                    }
                } else {
                    if (noir) { // si on doit comencer par distribuer les noirs
                        if (nbNoir > 0) {
                            posNext.getCell_players()[(i + incrPos + tour) % 12][0]++;
                            lastSeed = 0;
                            nbNoir--;
                        } else {
                            posNext.getCell_players()[(i + incrPos + tour) % 12][1]++;
                            lastSeed = 1;
                        }
                    } else {// si on doit comencer par distribuer les rouges
                        if (nbRouge > 0) {
                            posNext.getCell_players()[(i + incrPos + tour) % 12][1]++;
                            lastSeed = 1;
                            nbRouge--;
                        } else {
                            posNext.getCell_players()[(i + incrPos + tour) % 12][0]++;
                            lastSeed = 0;
                        }
                    }
                }
                tour++;
            }

        }
        else{// s'il n'y a pas de special à distribuer

            while(tour <= nb) {
                if ((tour + incrPos) % 12 == 0) {
                    incrPos++;
                }
                if (noir) { // si on doit comencer par distribuer les noirs
                    if (nbNoir > 0) {
                        posNext.getCell_players()[(i + incrPos + tour) % 12][0]++;
                        lastSeed = 0;
                        nbNoir--;
                    } else {
                        posNext.getCell_players()[(i + incrPos + tour) % 12][1]++;
                        lastSeed = 1;
                    }
                } else {// si on doit comencer par distribuer les rouges
                    if (nbRouge > 0) {
                        posNext.getCell_players()[(i + incrPos + tour) % 12][1]++;
                        lastSeed = 1;
                        nbRouge--;
                    } else {
                        posNext.getCell_players()[(i + incrPos + tour) % 12][0]++;
                        lastSeed = 0;
                    }
                }
                tour++;
            }
        }


        //vider la case de départ
        posNext.getCell_players()[i][0] = 0;
        posNext.getCell_players()[i][1] = 0;
        posNext.getCell_players()[i][2] = 0;



        //ramassage des graines
        tour = (i + incrPos + nb) % 12;
        if (computer_play) {
            while(tour > 5) {
                if (lastSeed == 0) {
                    nbNoir = posNext.getCell_players()[tour][0] + posNext.getCell_players()[tour][2];
                    if (nbNoir == 2 || nbNoir == 3) {
                        posNext.setSeed_computer(posNext.getSeed_computer() + nbNoir);
                        posNext.getCell_players()[tour][0] = 0;
                        posNext.getCell_players()[tour][2] = 0;
                        tour--;
                    }
                    else{tour = 4;}
                }
                if (lastSeed == 1) {
                    nbRouge = posNext.getCell_players()[tour][1] + posNext.getCell_players()[tour][2];
                    if (nbRouge == 2 || nbRouge == 3) {
                        posNext.setSeed_computer(posNext.getSeed_computer() + nbRouge);
                        posNext.getCell_players()[tour][1] = 0;
                        posNext.getCell_players()[tour][2] = 0;
                        tour--;
                    }
                    else{tour = 4;}
                }
                if (lastSeed == 2) {
                    nbNoir = posNext.getCell_players()[tour][0] + posNext.getCell_players()[tour][2];
                    nbRouge = posNext.getCell_players()[tour][1] + posNext.getCell_players()[tour][2];
                    if (nbNoir == 2 || nbNoir == 3) {
                        posNext.setSeed_computer(posNext.getSeed_computer() + nbNoir);
                        posNext.getCell_players()[tour][0] = 0;
                        posNext.getCell_players()[tour][2] = 0;
                        nbNoir = 2;

                    }
                    if(nbRouge == 2 || nbRouge == 3){
                        posNext.setSeed_computer(posNext.getSeed_computer() + posNext.getCell_players()[tour][1] + posNext.getCell_players()[tour][2]);
                        posNext.getCell_players()[tour][1] = 0;
                        posNext.getCell_players()[tour][2] = 0;
                        nbRouge = 2;

                    }
                    tour--;
                    if(nbRouge != 2 && nbNoir != 2){
                        tour = 4;
                    }
                    if(nbRouge == 2 && nbNoir != 2){
                        lastSeed = 1;
                    }
                    if(nbRouge != 2 && nbNoir == 2){
                        lastSeed = 0;
                    }
                }
            }
        }
        else{
            while(tour < 6 && tour > -1) {
                if (lastSeed == 0) {
                    nbNoir = posNext.getCell_players()[tour][0] + posNext.getCell_players()[tour][2];
                    if (nbNoir == 2 || nbNoir == 3) {
                        posNext.setSeed_player(posNext.getSeed_player() + nbNoir);
                        posNext.getCell_players()[tour][0] = 0;
                        posNext.getCell_players()[tour][2] = 0;
                        tour--;
                    }
                    else{tour = 8;}
                }
                if (lastSeed == 1) {
                    nbRouge = posNext.getCell_players()[tour][1] + posNext.getCell_players()[tour][2];
                    if (nbRouge == 2 || nbRouge == 3) {
                        posNext.setSeed_player(posNext.getSeed_player() + nbRouge);
                        posNext.getCell_players()[tour][1] = 0;
                        posNext.getCell_players()[tour][2] = 0;
                        tour--;
                    }
                    else{tour = 8;}
                }
                if (lastSeed == 2) {
                    nbNoir = posNext.getCell_players()[tour][0] + posNext.getCell_players()[tour][2];
                    nbRouge = posNext.getCell_players()[tour][1] + posNext.getCell_players()[tour][2];
                    if (nbNoir == 2 || nbNoir == 3) {
                        posNext.setSeed_player(posNext.getSeed_player() + nbNoir);
                        posNext.getCell_players()[tour][0] = 0;
                        posNext.getCell_players()[tour][2] = 0;
                        nbNoir = 2;

                    }
                    if(nbRouge == 2 || nbRouge == 3){
                        posNext.setSeed_player(posNext.getSeed_player() + posNext.getCell_players()[tour][1] + posNext.getCell_players()[tour][2]);
                        posNext.getCell_players()[tour][1] = 0;
                        posNext.getCell_players()[tour][2] = 0;
                        nbRouge = 2;
                    }
                    tour--;
                    if(nbRouge != 2 && nbNoir != 2){
                        tour = 8;
                    }
                    if(nbRouge == 2 && nbNoir != 2){
                        lastSeed = 1;
                    }
                    if(nbRouge != 2 && nbNoir == 2){
                        lastSeed = 0;
                    }
                }

            }
        }

    }

    public static int minMaxValue(Position pos_current, boolean computer_play,int depth,int depthMax,int alpha,int beta){
        // computer_play True si l'ordinateur doit jouer, False sinon
        boolean noir = true;
        int posSeed = 0;
        int indice = 0;
        int valeur;
        int x;
        boolean tempBool;

        Position pos_next = new Position();
        if (finalPosition(pos_current, computer_play)){
            // Écrire le code: renvoie VALMAX (= 96) si l'ordinateur gagne  , -96 s'il perd et 0 si tirage au sort
            if(computer_play){
                if(famine(pos_current,computer_play)){
                    return -96;
                }
            }
            else {
                if(famine(pos_current,computer_play)){
                    return 96;
                }
            }

            if(pos_current.getSeed_computer() > pos_current.getSeed_player()){
                return 96;
            }
            if(pos_current.getSeed_computer() < pos_current.getSeed_player()){
                return -96;
            }
            return 0;
        }
        if (depth == depthMax) {
            return evaluation(pos_current);
        }

        if(computer_play){
            valeur = -100;
            for(int i=0; i < 6;i++){
                if (validMove(pos_current, computer_play,i)){
                    if(pos_current.getCell_players()[i][2] == 0) { // pas de graine speciale
                        for (int j = 0; j < 2; j++) {  // j = 0 : on commence par le noir, j = 1, on commence par le rouge
                            if(pos_current.getCell_players()[i][j] > 0){
                                if(j == 0){ tempBool = true; }
                                else{ tempBool = false; }
                                playMove(pos_next, pos_current, computer_play, i, tempBool, 0);
                                x = minMaxValue(pos_next, !computer_play, depth + 1, depthMax,alpha,beta);
                                if (x > valeur) {
                                    valeur = x;
                                    noir = tempBool;
                                    posSeed = 0;
                                    indice = i;
                                }
                                if(valeur >= beta){
                                    actualiseVal(indice,posSeed,noir,depth);
                                    return valeur;
                                }
                                alpha = max(alpha, valeur);

                            }
                        }
                    }
                    else{ //Il y a une graine spéciale
                        for(int j = 1; j <= pos_current.getCell_players()[i][0] + pos_current.getCell_players()[i][1] + 1; j++){// pour toutes les positions de la spéciale
                            for (int k = 0; k < 2; k++) { // k = 0 : on commence par le noir, k = 1, on commence par le rouge
                                if(pos_current.getCell_players()[i][k] > 0) {
                                    if(k == 0){ tempBool = true; }
                                    else{ tempBool = false; }
                                    playMove(pos_next, pos_current, computer_play, i, tempBool, j);
                                    x = minMaxValue(pos_next, !computer_play, depth + 1, depthMax,alpha,beta);
                                    if(x > valeur){
                                        valeur = x;
                                        noir = tempBool;
                                        posSeed = j;
                                        indice = i;
                                    }
                                    if(valeur >= beta){
                                        actualiseVal(indice,posSeed,noir,depth);
                                        return valeur;
                                    }
                                    alpha = max(alpha, valeur);
                                }
                            }
                            if(pos_current.getCell_players()[i][0] == 0 && pos_current.getCell_players()[i][1] == 0) { // s'il n'y a que la spéciale
                                playMove(pos_next, pos_current, computer_play, i, true, j); // play noir par default
                                x = minMaxValue(pos_next, !computer_play, depth + 1, depthMax,alpha,beta);
                                if (x > valeur) {
                                    valeur = x;
                                    noir = true;
                                    posSeed = j;
                                    indice = i;
                                }
                                if(valeur >= beta){
                                    actualiseVal(indice,posSeed,noir,depth);
                                    return valeur;
                                }
                                alpha = max(alpha, valeur);
                            }
                        }
                    }
                }
            }
        }


        else{// !computer_play
            valeur = 100;
            for(int i=6;i<12;i++){
                if (validMove(pos_current, computer_play,i)){
                    if(pos_current.getCell_players()[i][2] == 0) { // pas de graine speciale

                        for (int j = 0; j < 2; j++) {  // j = 0 : on commence par le noir, j = 1, on commence par le rouge
                            if(pos_current.getCell_players()[i][j] > 0){
                                if(j == 0){ tempBool = true; }
                                else{ tempBool = false; }
                                playMove(pos_next, pos_current, computer_play, i, tempBool, 0);
                                x = minMaxValue(pos_next, !computer_play, depth + 1, depthMax,alpha,beta);
                                if (x < valeur) {
                                    valeur = x;
                                    noir = tempBool;
                                    posSeed = 0;
                                    indice = i;
                                }
                                /////////////////////////////////////
                                actualiseVal(indice,posSeed,noir,depth);
                                return valeur;
                                /////////////////////////
                                /*
                                if(alpha >= valeur){
                                    actualiseVal(indice,posSeed,noir,depth);
                                    return valeur;
                                }
                                beta = min(beta, valeur);
                                */
                            }
                        }
                    }
                    else{ //Il y a une graine spéciale
                        for(int j = 1; j <= pos_current.getCell_players()[i][0] + pos_current.getCell_players()[i][1] + 1; j++){// pour toutes les positions de la spéciale
                            for (int k = 0; k < 2; k++) { // k = 0 : on commence par le noir, k = 1, on commence par le rouge
                                if(pos_current.getCell_players()[i][k] > 0) {
                                    if(k == 0){ tempBool = true; }
                                    else{ tempBool = false; }
                                    playMove(pos_next, pos_current, computer_play, i, tempBool, j);
                                    x = minMaxValue(pos_next, !computer_play, depth + 1, depthMax,alpha,beta);
                                    if(x < valeur){
                                        valeur = x;
                                        noir = tempBool;
                                        posSeed = j;
                                        indice = i;
                                    }
                                    /////////////////////////////////////
                                    actualiseVal(indice,posSeed,noir,depth);
                                    return valeur;
                                    /////////////////////////
                                /*
                                if(alpha >= valeur){
                                    actualiseVal(indice,posSeed,noir,depth);
                                    return valeur;
                                }
                                beta = min(beta, valeur);
                                */
                                }
                            }

                            if(pos_current.getCell_players()[i][0] == 0 && pos_current.getCell_players()[i][1] == 0) { // s'il n'y a que la spéciale
                                playMove(pos_next, pos_current, computer_play, i, true, j); // play noir par default
                                x = minMaxValue(pos_next,!computer_play, depth + 1, depthMax,alpha,beta);
                                if (x < valeur) {
                                    valeur = x;
                                    noir = true;
                                    posSeed = j;
                                    indice = i;
                                }
                                /////////////////////////////////////
                                actualiseVal(indice,posSeed,noir,depth);
                                return valeur;
                                /////////////////////////
                                /*
                                if(alpha >= valeur){
                                    actualiseVal(indice,posSeed,noir,depth);
                                    return valeur;
                                }
                                beta = min(beta, valeur);
                                */
                            }
                        }
                    }
                }
            }
        }
        actualiseVal(indice,posSeed,noir,depth);
        return valeur;
    }


    public static void affichage(Position position){
        System.out.println("   ------- Computeur : " + position.getSeed_computer() + " __vs__ Player : " + position.getSeed_player() + " --------");
        System.out.println(" –––––––––––––––––––––––––––––––––––-----–––––––––––––");
        String s1 = "|",s2="|",s3="|";
        int i1,i2,i3,i4;
        if(FIRSTPLAYER) {
            i1 = 0;
            i2 = 6;
            i3 = 11;
            i4 = 5;

        }
        else{
            i1 = 6;
            i2 = 12;
            i3 = 5;
            i4 = -1;
        }
        for (int i = i1; i < i2; i++) {
            if (position.getCell_players()[i][0] > 9) {
                s1 += " N : " + position.getCell_players()[i][0] + " |";
            } else {
                s1 += " N :  " + position.getCell_players()[i][0] + " |";
            }
            if (position.getCell_players()[i][1] > 9) {
                s2 += " R : " + position.getCell_players()[i][1] + " |";
            } else {
                s2 += " R :  " + position.getCell_players()[i][1] + " |";
            }
            if (position.getCell_players()[i][2] > 9) {
                s3 += " S : " + position.getCell_players()[i][2] + " |";
            } else {
                s3 += " S :  " + position.getCell_players()[i][2] + " |";
            }
        }

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(" ––––––––––––––––––––––––––––––––––––––––-----––––––––");
        s1 = "|";s2="|";s3="|";
        for (int i = i3; i > i4; i--) {
            if(position.getCell_players()[i][0] > 9){
                s1 +=  " N : " +position.getCell_players()[i][0]+" |";
            }
            else{
                s1 +=  " N :  " +position.getCell_players()[i][0]+" |";
            }
            if(position.getCell_players()[i][1] > 9){
                s2 +=  " R : " +position.getCell_players()[i][1]+" |";
            }
            else{
                s2 +=  " R :  " +position.getCell_players()[i][1]+" |";
            }
            if(position.getCell_players()[i][2] > 9){
                s3 +=  " S : " +position.getCell_players()[i][2]+" |";
            }
            else{
                s3 +=  " S :  " +position.getCell_players()[i][2]+" |";
            }
        }
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(" ––––––––––––––––––––-----––––––––––––––––––––––––––––");

    }

    public static void startGame(Position pos_current, boolean computer_play){
        for(int i=0;i<12;i++){
            pos_current.getCell_players()[i][0] = 3;
            pos_current.getCell_players()[i][1] = 3;
            pos_current.getCell_players()[i][2] = 0;
        }
        pos_current.setComputerplay(computer_play);
        pos_current.setSeed_player(0);
        pos_current.setSeed_computer(0);
    }

    public static void startGameTest(Position pos_current, boolean computer_play){
        int[][] tab = {{0,0,0},{0,0,0},{0,0,0},{0,3,0},{3,4,0},{0,0,0},
                {0,0,0},{2,2,0},{1,1,0},{0,0,0},{0,3,0},{0,0,0}};
        pos_current.setCell_players(tab);
        pos_current.setComputerplay(computer_play);
        pos_current.setSeed_player(0);
        pos_current.setSeed_computer(0);
    }


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Position position = new Position();
        String firstPlayer = "a";

        int indiceAdversaire = -1;
        boolean couleurAdversaire;
        String stringCouleurAdversaire = "A";
        int posSeedJoueur = -1;
        int profondeur = 7;

        System.out.println( "L'ordinateur commence?" );
        while (!firstPlayer.equals("o") && !firstPlayer.equals("n") ) {
            System.out.println("Entrez 'o' : oui ou 'n' : non.");
            firstPlayer = scan.nextLine();
        }
        if(firstPlayer.equals("o")){
            FIRSTPLAYER = true;
        }
        else{
            FIRSTPLAYER = false;
        }

        startGame(position,FIRSTPLAYER);

        affichage(position);

        //************************************** gérer la graine spéciale au départ *************************************
        System.out.println("Où voulez vous placer la graine spéciale?");
        if(FIRSTPLAYER){//case de l'adversaire 7 à 12
            System.out.println("L'ordinateur place sa graine en 3.");
            while (posSeedJoueur < 1 || posSeedJoueur > 12) {
                System.out.println( "Entrez un nombre entre 1 et 12." );
                posSeedJoueur = scan.nextInt();
            }
            posSeedJoueur--;

        }
        else{//case de l'adversaire 1 à 6
            while (posSeedJoueur < 1 || posSeedJoueur > 12) {
                System.out.println("Entrez un nombre entre 1 et 12.");
                posSeedJoueur = scan.nextInt();
            }
            posSeedJoueur = (posSeedJoueur+5) % 12;
            System.out.println("L'ordinateur place sa graine en 9." );

        }

        position.getCell_players()[2][2]++;

        position.getCell_players()[posSeedJoueur][2]++;
        posSeedJoueur = -1;

        while (!finalPosition(position,position.isComputerplay())){

            if(position.isComputerplay()) {
                if(profondeur == 7) {
                    if (position.getCell_players()[0][2] + position.getCell_players()[1][2] + position.getCell_players()[2][2] +
                            position.getCell_players()[3][2] + position.getCell_players()[4][2] + position.getCell_players()[5][2] +
                            position.getCell_players()[6][2] + position.getCell_players()[7][2] + position.getCell_players()[8][2] +
                            position.getCell_players()[9][2] + position.getCell_players()[10][2] + position.getCell_players()[11][2] == 1) {
                        profondeur = 9;
                    }
                }
                minMaxValue(position, true,0,profondeur,MIN,MAX);
                playMove(position,position,true,INDICE , COULEUR, POSSEED);
                position.setComputerplay(!position.isComputerplay());
                affichage(position);
            }
            else{
                System.out.println("Quelle case jouer?");
                if(FIRSTPLAYER){//case de l'adversaire 7 à 12
                    while (indiceAdversaire < 7 || indiceAdversaire > 12) {
                        System.out.println("Entrez un nombre entre 7 et 12.");
                        indiceAdversaire = scan.nextInt();
                    }
                    indiceAdversaire--;

                }
                else{//case de l'adversaire 1 à 6
                    while (indiceAdversaire < 1 || indiceAdversaire > 6) {
                        System.out.println("Entrez un nombre entre 1 et 6.");
                        indiceAdversaire = scan.nextInt();
                    }
                    indiceAdversaire += 5;

                }

                System.out.println("Quelle couleur voulez vous jouer?");
                while (!stringCouleurAdversaire.equals("N") && !stringCouleurAdversaire.equals("R")) { // régler la couleur
                    System.out.println("Entrez R : Rouge et N : Noir.");
                    stringCouleurAdversaire = scan.nextLine();
                }

                if(stringCouleurAdversaire.equals("N")){
                    couleurAdversaire = true;
                }
                else{
                    couleurAdversaire = false;
                }

                System.out.println("Ou voulez vous placer la graine spéciale?");
                while (posSeedJoueur < 0) {
                    System.out.println("Entrez un nombre positif.");
                    posSeedJoueur = scan.nextInt();
                    //faire une vérif si sa pos seed est correct
                }
                if(position.getCell_players()[indiceAdversaire][2] == 0 && posSeedJoueur > 0){
                    System.out.println("Movement invalide : pas de spéciale à déplacer.");
                    System.out.println( "On s'arrete? Entrez une nouvelle valeur" );
                    posSeedJoueur = scan.nextInt();

                }
                if(validMove(position,position.isComputerplay(),indiceAdversaire)){
                    playMove(position,position,position.isComputerplay(),indiceAdversaire,couleurAdversaire,posSeedJoueur);
                    position.setComputerplay(!position.isComputerplay());
                    indiceAdversaire = -1;
                    stringCouleurAdversaire = "A";
                    posSeedJoueur = -1;
                }
                else{
                    System.out.println("Le coup n'est pas valide");
                    System.out.println("On s'arrete?");
                    posSeedJoueur = scan.nextInt();
                }
                affichage(position);
            }

        }
        if(position.getSeed_computer() > position.getSeed_player()){
            System.out.println("Victoire de l'ordinateur : " + position.getSeed_computer() + " à " + position.getSeed_player());
        }
        if(position.getSeed_computer() < position.getSeed_player()){
            System.out.println("Victoire de l'adversaire : " + position.getSeed_player() + " à " + position.getSeed_computer());
        }
        if(position.getSeed_computer() == position.getSeed_player()){
            System.out.println("Egalité : " + position.getSeed_player() + " à " + position.getSeed_computer());
        }

    }

}


