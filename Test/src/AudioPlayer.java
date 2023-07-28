public class AudioPlayer extends Player{
    void play(int pos){
        System.out.println("상속받은 play 실행?");
    }

    void stop() {
        System.out.println("상속받은 stop 실행?");
    }
}