package ru.gb.family_tree.view;

import ru.gb.family_tree.presenter.Presenter;

import java.time.LocalDate;
import java.util.Scanner;

public class ConsoleUI implements View {
    private final Scanner scanner;
    private final Presenter presenter;
    private final MainMenu mainMenu;

    private boolean work;

    public ConsoleUI() {
        scanner = new Scanner(System.in);
        presenter = new Presenter(this);
        mainMenu = new MainMenu(this);
        work = true;
    }

    @Override
    public void start() {
        hello();
        while (work) {
            int choice = checkUserChoice(getUserChoice());
            if (choice != 0)
                mainMenu.execute(choice);
            else
                System.out.println("Введите номер команды...");
        }
    }

    private int checkUserChoice(String userChoice) {
        int choice = 0;
        if (userChoice.matches("\\d+")) {
            choice = Integer.parseInt(userChoice);
        } else {
            return choice;
        }
        if ((choice > 0) && (choice <= mainMenu.getNumberOfCommands()))
            return choice;
        else {
            choice = 0;
        }
        return choice;
    }

    private String getUserChoice() {
        System.out.println(mainMenu.getMenu());
        return scanner.nextLine();
    }

    private void hello() {
        System.out.println("Приветствие.");
    }

    @Override
    public void printAnswer(String text) {
        System.out.println(text);
    }

    public void addNewSubjectToTree() {

        ViewGender choiceGender = getViewGender();

        System.out.println("Введите имя: ");
        String name = scanner.nextLine();

        System.out.println("Введите год рождения: ");
        String strYear = scanner.nextLine();
        int year = 2000;
        if (tryToInt(strYear)) {
            year = Integer.parseInt(strYear);
        } else {
            System.out.println("Некорректное значение, текущее значение: 2000");
        }

        System.out.println("Введите номер месяца рождения: ");
        String strMonth = scanner.nextLine();
        int month = 1;
        if (tryToInt(strMonth)) {
            month = Integer.parseInt(strMonth);
        } else {
            System.out.println("Некорректное значение, текущее значение: 1");
        }

        System.out.println("Введите день рождения: ");
        String strDayOfMonth = scanner.nextLine();
        int dayOfMonth = 1;
        if (tryToInt(strDayOfMonth)) {
            dayOfMonth = Integer.parseInt(strDayOfMonth);
        } else {
            System.out.println("Некорректное значение, текущее значение: 1");
        }

        presenter.addObject(name, LocalDate.of(year, month, dayOfMonth), choiceGender.toString());

        printAnswer(presenter.getTree());
    }

    private ViewGender getViewGender() {
        ViewGender choiceGender = ViewGender.NULL;
        boolean isGenderCheck = false;
        while (!isGenderCheck) {
            choiceGender = genderChecking();
            if (choiceGender != ViewGender.NULL)
                isGenderCheck = true;
        }
        return choiceGender;
    }

    private ViewGender genderChecking() {
        System.out.println("Выберите пол: ");
        System.out.println("1 - М");
        System.out.println("2 - Ж");
        ViewGender viewGender = checkUserChoiceOfGender(scanner.nextLine());
        if (viewGender == ViewGender.NULL) {
            System.out.println("Повторите выбор.");
        }
        return viewGender;
    }

    private ViewGender checkUserChoiceOfGender(String userChoice) {
        int choice;
        ViewGender viewGender = ViewGender.NULL;
        if (userChoice.matches("\\d+")) {
            choice = Integer.parseInt(userChoice);
        } else {
            return viewGender;
        }
        if ((choice > 0) && (choice < ViewGender.values().length)) {
            if (choice == 1) {
                viewGender = ViewGender.Male;
            } else if (choice == 2) {
                viewGender = ViewGender.Female;
            }
            return viewGender;
        }
        return viewGender;
    }

    public void getTree() {
        printAnswer(presenter.getTree());
    }

    public boolean tryToInt(String str) {
        return str.matches("\\d+");
    }

    public void finish() {
        work = false;
    }

    public void sortByAge() {
        presenter.sortByAge();
        printAnswer(presenter.getTree());
    }

    public void sortByName() {
        presenter.sortByName();
        printAnswer(presenter.getTree());
    }

    public void saveTree() {
        boolean saveSuccess = presenter.saveTree();
        if (saveSuccess) {
            System.out.println("Изменения сохранены.");
        }
    }

    public void readTree() {
        presenter.readTree();
        printAnswer(presenter.getTree());
    }

    public void addParent() {
        printAnswer(presenter.getTree());
        System.out.println("Введите ID родителя: ");
        String strParentID = scanner.nextLine();
        int parentID = Integer.parseInt(strParentID);

        System.out.println("Введите ID ребёнка: ");
        String strChildID = scanner.nextLine();
        int childID = Integer.parseInt(strChildID);

        boolean parentAdded = presenter.addParentByID(parentID, childID);
        if (parentAdded) {
            printAnswer(presenter.getTree());
        } else {
            System.out.println("Введено некорректное значение.");
        }
    }
}
