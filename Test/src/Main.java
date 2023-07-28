public class Main{
    public static void main(String[] args) {

        System.out.println("//Player");

        Player player = new Player() {
            @Override
            void play(int pos) {
                System.out.println(pos + "Player");
            }

            @Override
            void stop() {
                System.out.println("Stopped By Player");
            }
        };
        player.play(2);
        player.stop();
        player.pause();



        System.out.println("//AbstractPlayer");

        AbstractPlayer abstractPlayer = new AbstractPlayer() {
            @Override
            void play(int pos) {
                System.out.println(pos + "abstractPlayer");
            }
            @Override
            void stop() {
                System.out.println("Stopped By AbstractPlayer");
            }
        };

        abstractPlayer.player(3);
        abstractPlayer.play();
        abstractPlayer.pause();
        abstractPlayer.stop();

        System.out.println("//AudioPlayer");

        AudioPlayer audioPlayer = new AudioPlayer();
        audioPlayer.play();
        audioPlayer.pause();
        audioPlayer.stop();
        audioPlayer.play(3);


    }
}