package ru.gb.family_tree.model.human;

import ru.gb.family_tree.model.tree.TreeElement;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Human implements Serializable, Comparable<Human>, TreeElement {
    private int id;
    private String name;
    private LocalDate birth, death;
    private Gender gender;
    private List<Human> children;
    private Human mother, father;

    public Human(String name, LocalDate birth, LocalDate death, Gender gender, List<Human> children, Human mother,
            Human father) {
        this.id = -1;
        this.name = name;
        this.birth = birth;
        this.death = death;
        this.gender = gender;
        this.children = children;
        this.mother = mother;
        this.father = father;
    }

    public Human(String name, LocalDate birth, Gender gender) {
        this(name, birth, null, gender, null, null, null);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getbirth() {
        return birth;
    }

    public LocalDate getdeath() {
        return death;
    }

    public Gender getGender() {
        return gender;
    }

    public List<Human> getChildren() {
        return children;
    }

    public String getChildrenNames() {
        StringBuilder childrenString = new StringBuilder();
        childrenString.append("{");
        if (this.getChildren() != null) {
            for (Human child : this.getChildren()) {
                childrenString.append(child.name);
                childrenString.append(", ");
            }
            childrenString.deleteCharAt(childrenString.length() - 1);
            childrenString.deleteCharAt(childrenString.length() - 1);
            childrenString.append("}");
        } else {
            return null;
        }
        return childrenString.toString();
    }

    public Human getMother() {
        return mother;
    }

    public Human getFather() {
        return father;
    }

    private int getPeriod(LocalDate start, LocalDate end) {
        Period diff = Period.between(start, end);
        return diff.getYears();
    }

    public int getAge() {
        if (death == null) {
            return getPeriod(birth, LocalDate.now());
        } else {
            return getPeriod(birth, death);
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setbirth(LocalDate birth) {
        this.birth = birth;
    }

    public void setdeath(LocalDate death) {
        this.death = death;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setChildren(List<Human> children) {
        this.children = children;
    }

    public void setMother(Human mother) {
        this.mother = mother;
    }

    public void setFather(Human father) {
        this.father = father;
    }

    public void addChild(Human child) {
        if (this.gender == gender.Male) {
            child.setFather(this);
        }
        if (this.gender == gender.Female) {
            child.setMother(this);
        }
        if (this.children == null) {
            children = new ArrayList<>();
        }
        this.children.add(child);
    }

    public void addParent(Human parent) {
        if (parent.gender == gender.Male) {
            this.setFather(parent);
        }
        if (parent.gender == gender.Female) {
            this.setMother(parent);
        }
        parent.addChild(this);
    }

    @Override
    public String toString() {
        return getInfo();
    }

    private String getInfo() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("ID: ");
        stringBuilder.append(id);

        stringBuilder.append(", имя: ");
        stringBuilder.append(name);

        stringBuilder.append(", дата рождения: ");
        stringBuilder.append(birth);

        stringBuilder.append(", пол: ");
        stringBuilder.append(gender);

        stringBuilder.append(", дата смерти: ");
        if (death != null) {
            stringBuilder.append(death);
        } else {
            stringBuilder.append("неизвестно");
        }

        stringBuilder.append(", отец: ");
        if (father != null) {
            stringBuilder.append(getFather().getName());
        } else {
            stringBuilder.append("неизвестно");
        }

        stringBuilder.append(", мать: ");
        if (mother != null) {
            stringBuilder.append(getMother().getName());
        } else {
            stringBuilder.append("неизвестно");
        }

        stringBuilder.append(", дети: ");
        if (children != null) {
            stringBuilder.append(getChildrenNames());
        } else {
            stringBuilder.append("неизвевстно;");
        }

        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Human o) {
        return this.name.compareTo(o.name);
    }
}
