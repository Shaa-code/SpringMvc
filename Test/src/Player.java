abstract class Player{

    abstract void play(int pos);
    abstract void stop();

    void play(){
        System.out.println("play");
    }

    void pause(){
        System.out.println("pause");
    }
}