package com.lab8;

public class Data {
    private String firstName;
    private String lastName;
    private final String email;
    private final String imageLink;
    private final String ipAddress;

    public Data(String firstName, String lastName, String email, String imageLink, String ipAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imageLink = imageLink;
        this.ipAddress = ipAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Data uppercaseFirstName() {
        firstName = firstName.toUpperCase();
        return this;
    }

    public Data uppercaseLastName() {
        lastName = lastName.toUpperCase();
        return this;
    }

    public Data lowercaseFirstName() {
        firstName = firstName.toLowerCase();
        return this;
    }

    public Data lowercaseLastName() {
        lastName = lastName.toLowerCase();
        return this;
    }

    public Data capitalizeFirstName() {
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        return this;
    }

    public Data capitalizeLastName() {
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        return this;
    }

    public String getIpKey() {
        return ipAddress.substring(0, 3);
    }

    public static Data getDataFromLine(String line) {
        String[] values = line.split(";");
        return new Data(values[0], values[1], values[2], values[3], values[4]);
    }
}
