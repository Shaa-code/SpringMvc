import java.util.ArrayList;
import java.util.List;

public class Main{
    public static void main(String[] args) {

        Member member = new Member();
        member.setUsername("userA");

        Team team = new Team();
        team.setName("teamA");

        member.changeTeam(team);

        System.out.println(
                "team = " + team + "\n" +
                "teamName = " + team.getName() + "\n" +
                "hashCode = " + team.hashCode()
        );

        System.out.println("member = " + member.getTeam() + "\n" +
                "teamName = " + member.getTeam().getName() + "\n" +
                "hashCode = " + member.getTeam().hashCode()
        );

        System.out.println(team.getMembers());


    }

    static class Team{
        private String name;
        private List<Member> members = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Member> getMembers() {
            return members;
        }

        public void setMembers(List<Member> members) {
            this.members = members;
        }

        @Override
        public String toString() {
            return "Team{" +
                    "name='" + name + '\'' +
                    ", members=" + members +
                    '}';
        }
    }

    static class Member{
        private String username;
        private Team team;

        public void changeTeam(Team team) {
            this.team = team;
            team.getMembers().add(this);
            team.setName("Changed!");
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Team getTeam() {
            return team;
        }

        public void setTeam(Team team) {
            this.team = team;
        }

        @Override
        public String toString() {
            return "Member{" +
                    "username='" + username + '\'' +
                    ", team=" + team +
                    '}';
        }
    }

}