package com.lab6;

public class User {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String imageLink;
    private final String ipAddress;
    private final String filename;

    public User(String firstName, String lastName, String email, String imageLink, String ipAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imageLink = imageLink;
        this.ipAddress = ipAddress;

        String firstNameSub = firstName.length() > 2 ? firstName.substring(0, 3) : firstName;
        String lastNameSub = lastName.length() > 2 ? lastName.substring(0, 3) : lastName;
        filename = firstNameSub + lastNameSub + ipAddress.substring(ipAddress.lastIndexOf('.') + 1) + ".csv";
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

    public String getFilename() {
        return filename;
    }

    public static User getUserFromLine(String line) {
        String[] values = line.split(";");
        return new User(values[0], values[1], values[2], values[3], values[4]);
    }
}