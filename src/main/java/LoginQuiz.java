import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LoginQuiz {
    private static final String filename = "./src/main/resources/users.json";

    public static void main(String[] args) throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Username");
        String username = scanner.nextLine();
        System.out.println("Username : " + username);
        System.out.println("Enter Password");
        String password = scanner.nextLine();
        System.out.println("Password : " + password);


        JSONParser jsonParser = new JSONParser();
        JSONArray userArray = (JSONArray) jsonParser.parse(new FileReader (filename));

        JSONObject adminObject = (JSONObject) userArray.get(0);
        JSONObject studentObject = (JSONObject) userArray.get(1);


        if (adminObject.get("username").equals(username) && adminObject.get("password").equals(password)) {
            addNewQuestions();
        } else if(studentObject.get("username").equals(username) && studentObject.get("password").equals(password)){
            attempToQuiz ();
        }else{
            System.out.println("User not found");
        }

    }

    private static void addNewQuestions() throws IOException, ParseException {
        System.out.println("Welcome admin! Please create new questions in the question bank.");

        while (true){
            System.out.println("Input your questions");
            Scanner scanner = new Scanner(System.in);
            String question = scanner.nextLine();

            String[] options = new String[4];
            for (int i = 0; i < 4; i++) {
                System.out.print("option " + (i + 1) + ":");
                options[i] = scanner.nextLine();
            }

            System.out.println("What is the answer key? ");
            scanner = new Scanner(System.in);
            int answerKey = scanner.nextInt();

            saveQuestions(question,options,answerKey);

            System.out.print("Saved successfully! Do you want to add more questions? (press s for start and q for quit)");
            scanner = new Scanner(System.in);
            String response = scanner.nextLine();
            if (response.equals("q")) {
                break;
            }
        }
    }

    private static void saveQuestions(String question, String[] options, int answerKey) throws IOException, ParseException {
        String fileName = "./src/main/resources/quiz.json";

        JSONParser jsonParser = new JSONParser();
        JSONArray quizArray = (JSONArray) jsonParser.parse(new FileReader(fileName));

        JSONObject newQuestion = new JSONObject();
        newQuestion.put("question", question);

        newQuestion.put("option 1",options[0]);
        newQuestion.put("option 2",options[1]);
        newQuestion.put("option 3",options[2]);
        newQuestion.put("option 4",options[3]);

        newQuestion.put("answerKey", answerKey);

        quizArray.add(newQuestion);

        FileWriter fw = new FileWriter(fileName);
        fw.write(quizArray.toJSONString());
        fw.flush();
        fw.close();
    }


    private static void attempToQuiz() throws IOException, ParseException {
        String fileName = "./src/main/resources/quiz.json";
        System.out.println("Welcome to the quiz! We will throw you 10 questions. Each MCQ mark is 1 and no negative marking. Are you ready? Press 's' for start.");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String startQuiz = scanner.nextLine();
            if (startQuiz.equals("s")) {
                break;
            }
        }

        int score = 0;
        int totalQuestions = 0;


        JSONParser jsonParser = new JSONParser();
        JSONArray quizArray = (JSONArray) jsonParser.parse(new FileReader(fileName));


        for (int i = 0; i < 10 && i < quizArray.size(); i++) {

            int randomIndex = (int) (Math.random() * quizArray.size());
            JSONObject question = (JSONObject) quizArray.get(randomIndex);

            System.out.println("[Question " + (totalQuestions + 1) + "] " + question.get("question"));
            for (int j = 1; j <= 4; j++) {
                System.out.println(j + ". " + question.get("option " + j));
            }

            System.out.print("Answer: ");
            int userAnswer;
            userAnswer = scanner.nextInt();

            int correctAnswer = ((Long) question.get("answerKey")).intValue();
            if (userAnswer == correctAnswer) {
                score++;
            }

            totalQuestions++;
        }

        System.out.println("Quiz Completed!");
        System.out.println(" Your Score: " + score + " out of " + totalQuestions);

        if (score >= 8) {
            System.out.println(" Excellent! You have got " + score + " out of " + totalQuestions);
        } else if (score >= 5) {
            System.out.println("Good. You have got " + score + " out of " + totalQuestions);
        } else if (score >= 2) {
            System.out.println("Very poor! You have got " + score + " out of " + totalQuestions);
        } else {
            System.out.println("Very sorry, you are failed. You have got " + score + " out of " + totalQuestions);
        }

    }
}
