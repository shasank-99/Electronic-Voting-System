import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;

class ElectronicVotingSystem {

    private HashMap<String, String> voteLedger = new HashMap<>();
    private HashMap<Integer, String> candidates = new HashMap<>();

    public ElectronicVotingSystem() {
        // Initialize candidates (for simplicity, we use IDs)
        candidates.put(1, "Candidate A");
        candidates.put(2, "Candidate B");
        candidates.put(3, "Candidate C");
    }

    public void castVote(String voterId, int candidateId) {
        if (candidates.containsKey(candidateId)) {
            String voteHash = generateHash(voterId + candidateId);
            voteLedger.put(voterId, voteHash);
            System.out.println("Vote successfully cast for " + candidates.get(candidateId));
        } else {
            System.out.println("Invalid candidate ID.");
        }
    }

    public void showResults() {
        HashMap<String, Integer> results = new HashMap<>();
        for (String vote : voteLedger.values()) {
            String candidateId = vote.substring(vote.length() - 1);
            String candidate = candidates.get(Integer.parseInt(candidateId));
            results.put(candidate, results.getOrDefault(candidate, 0) + 1);
        }

        System.out.println("Election Results:");
        for (String candidate : results.keySet()) {
            System.out.println(candidate + ": " + results.get(candidate) + " votes");
        }
    }

    private String generateHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ElectronicVotingSystem votingSystem = new ElectronicVotingSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nElectronic Voting System");
            System.out.println("1. Cast Vote");
            System.out.println("2. Show Results");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter your Voter ID: ");
                    String voterId = scanner.next();
                    System.out.println("Candidates:");
                    System.out.println("1. Candidate A");
                    System.out.println("2. Candidate B");
                    System.out.println("3. Candidate C");
                    System.out.print("Enter Candidate ID to vote for: ");
                    int candidateId = scanner.nextInt();
                    votingSystem.castVote(voterId, candidateId);
                    break;
                case 2:
                    votingSystem.showResults();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
