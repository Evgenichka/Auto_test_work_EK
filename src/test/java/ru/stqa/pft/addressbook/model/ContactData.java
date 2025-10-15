//package ru.stqa.pft.addressbook.model;
//
//import lombok.Data;
//
//@Data
//public class ContactData {
//
//    // Метод доступа для поля firstName
//    private String firstName;
//    private String middleName;
//    // Метод доступа для поля lastName
//    private String lastName;
//    private String nickname;
//    private String company;
//    private String email;
//    private String group;
//
//    // Основной конструктор с полным набором полей
//    public ContactData(String firstName, String middleName, String lastName, String nickname, String company, String email, String group) {
//        this.firstName = firstName;
//        this.middleName = middleName;
//        this.lastName = lastName;
//        this.nickname = nickname;
//        this.company = company;
//        this.email = email;
//        this.group = group;
//    }
//
//    // Альтернативный конструктор без обязательных полей
//    public ContactData(String firstName, String lastName) {
//        this(firstName, "", lastName, "", "", "", "");
//    }
//
//    // Дополнительный конструктор с частичным заполнением
//    public ContactData(String firstName, String lastName, String email) {
//        this(firstName, "", lastName, "", "", email, "");
//    }
//
//
//    // Метод доступа для поля lastName
//    public String getLastName() {
//        return lastName;
//    }
//
//    // Метод доступа для поля firstName
//    public String getFirstName() {
//        return firstName;
//    }
//}

package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class ContactData {

    private String firstName;
    private String middleName;
    private String lastName;
    private String nickname;
    private String title;
    private String company;
    private String address;
    private String homePhone;
    private String mobilePhone;
    private String workPhone;
    private String fax;
    private String email;
    private String email2;
    private String email3;
    private String homepage;
    private String birthdayDay;
    private String birthdayMonth;
    private String birthdayYear;
    private String anniversaryDay;
    private String anniversaryMonth;
    private String anniversaryYear;
    private String secondaryAddress;
    private String phone2;
    private String notes;
    private String photoPath;
    private String group; // Новое поле для группы

    // Конструктор с минимальным набором полей
    public ContactData(String firstName, String middleName, String lastName, String nickname,
                       String company, String email, String group) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.company = company;
        this.email = email;
        this.group = group;
    }

    // Полный конструктор с всеми полями
    public ContactData(String firstName, String middleName, String lastName, String nickname,
                       String title, String company, String address, String homePhone,
                       String mobilePhone, String workPhone, String fax, String email,
                       String email2, String email3, String homepage, String birthdayDay,
                       String birthdayMonth, String birthdayYear, String anniversaryDay,
                       String anniversaryMonth, String anniversaryYear, String secondaryAddress,
                       String phone2, String notes, String photoPath, String group) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.title = title;
        this.company = company;
        this.address = address;
        this.homePhone = homePhone;
        this.mobilePhone = mobilePhone;
        this.workPhone = workPhone;
        this.fax = fax;
        this.email = email;
        this.email2 = email2;
        this.email3 = email3;
        this.homepage = homepage;
        this.birthdayDay = birthdayDay;
        this.birthdayMonth = birthdayMonth;
        this.birthdayYear = birthdayYear;
        this.anniversaryDay = anniversaryDay;
        this.anniversaryMonth = anniversaryMonth;
        this.anniversaryYear = anniversaryYear;
        this.secondaryAddress = secondaryAddress;
        this.phone2 = phone2;
        this.notes = notes;
        this.photoPath = photoPath;
        this.group = group;
    }

    // Геттеры и сеттеры для всех полей

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getEmail3() {
        return email3;
    }

    public void setEmail3(String email3) {
        this.email3 = email3;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getBirthdayDay() {
        return birthdayDay;
    }

    public void setBirthdayDay(String birthdayDay) {
        this.birthdayDay = birthdayDay;
    }

    public String getBirthdayMonth() {
        return birthdayMonth;
    }

    public void setBirthdayMonth(String birthdayMonth) {
        this.birthdayMonth = birthdayMonth;
    }

    public String getBirthdayYear() {
        return birthdayYear;
    }

    public void setBirthdayYear(String birthdayYear) {
        this.birthdayYear = birthdayYear;
    }

    public String getAnniversaryDay() {
        return anniversaryDay;
    }

    public void setAnniversaryDay(String anniversaryDay) {
        this.anniversaryDay = anniversaryDay;
    }

    public String getAnniversaryMonth() {
        return anniversaryMonth;
    }

    public void setAnniversaryMonth(String anniversaryMonth) {
        this.anniversaryMonth = anniversaryMonth;
    }

    public String getAnniversaryYear() {
        return anniversaryYear;
    }

    public void setAnniversaryYear(String anniversaryYear) {
        this.anniversaryYear = anniversaryYear;
    }

    public String getSecondaryAddress() {
        return secondaryAddress;
    }

    public void setSecondaryAddress(String secondaryAddress) {
        this.secondaryAddress = secondaryAddress;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    // Геттер и сеттер для группы
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    // Метод equals для сравнения объектов
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactData)) return false;
        ContactData that = (ContactData) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(middleName, that.middleName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(title, that.title) &&
                Objects.equals(company, that.company) &&
                Objects.equals(address, that.address) &&
                Objects.equals(homePhone, that.homePhone) &&
                Objects.equals(mobilePhone, that.mobilePhone) &&
                Objects.equals(workPhone, that.workPhone) &&
                Objects.equals(fax, that.fax) &&
                Objects.equals(email, that.email) &&
                Objects.equals(email2, that.email2) &&
                Objects.equals(email3, that.email3) &&
                Objects.equals(homepage, that.homepage) &&
                Objects.equals(birthdayDay, that.birthdayDay) &&
                Objects.equals(birthdayMonth, that.birthdayMonth) &&
                Objects.equals(birthdayYear, that.birthdayYear) &&
                Objects.equals(anniversaryDay, that.anniversaryDay) &&
                Objects.equals(anniversaryMonth, that.anniversaryMonth) &&
                Objects.equals(anniversaryYear, that.anniversaryYear) &&
                Objects.equals(secondaryAddress, that.secondaryAddress) &&
                Objects.equals(phone2, that.phone2) &&
                Objects.equals(notes, that.notes) &&
                Objects.equals(photoPath, that.photoPath) &&
                Objects.equals(group, that.group); // Добавляем сравнение групп
    }

    // Метод hashCode для правильной работы коллекций
    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName, nickname, title, company, address,
                homePhone, mobilePhone, workPhone, fax, email, email2, email3,
                homepage, birthdayDay, birthdayMonth, birthdayYear, anniversaryDay,
                anniversaryMonth, anniversaryYear, secondaryAddress, phone2, notes, photoPath, group);
    }

    // Метод toString для удобной печати объекта
    @Override
    public String toString() {
        return "ContactData{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", address='" + address + '\'' +
                ", homePhone='" + homePhone + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", workPhone='" + workPhone + '\'' +
                ", fax='" + fax + '\'' +
                ", email='" + email + '\'' +
                ", email2='" + email2 + '\'' +
                ", email3='" + email3 + '\'' +
                ", homepage='" + homepage + '\'' +
                ", birthdayDay='" + birthdayDay + '\'' +
                ", birthdayMonth='" + birthdayMonth + '\'' +
                ", birthdayYear='" + birthdayYear + '\'' +
                ", anniversaryDay='" + anniversaryDay + '\'' +
                ", anniversaryMonth='" + anniversaryMonth + '\'' +
                ", anniversaryYear='" + anniversaryYear + '\'' +
                ", secondaryAddress='" + secondaryAddress + '\'' +
                ", phone2='" + phone2 + '\'' +
                ", notes='" + notes + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", group='" + group + '\'' + // Добавляем вывод группы
                '}';
    }
}