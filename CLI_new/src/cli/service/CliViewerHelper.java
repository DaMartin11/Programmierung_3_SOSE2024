package cli.service;

import contract.Tag;
import contract.Uploader;
import domainLogic.UploaderImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;


public class CliViewerHelper {

    public static int getSamplingRateFromUser() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Sampling Rate: ");
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                sc.next(); // consume invalid input
            }
        }
    }

    public static String getAddressFromUser() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Address: ");
        return sc.next();
    }

    public static Collection<Tag> getTagsFromUser() {
        Scanner sc = new Scanner(System.in);

        Collection<Tag> tags = new ArrayList<>();
        boolean validInput = false;

        System.out.println("Please enter one or more tags, comma-separated:");

        for (Tag tag : Tag.values()) {
            System.out.println(tag);
        }
        while (!validInput) {

            String tagsInput = sc.nextLine();
            String[] tagNames = tagsInput.split(",");
            if (!tagsInput.isEmpty()) {
                for (String tagName : tagNames) {
                    try {
                        Tag tag = Tag.valueOf(tagName.trim());
                        tags.add(tag);
                        validInput = true; // Mark input as valid if no exception is thrown
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid tag: " + tagName.trim());
                        tags.clear(); // Clear tags if any invalid tag is encountered
                        validInput = false; // Reset validInput flag
                        break; // Break the loop to re-enter tags
                    }
                }
            }
        }
        return tags;
    }

    public static long getSizeFromUser() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Size: ");
                return sc.nextLong();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a long integer for size.");
                sc.next(); // consume invalid input
            }
        }
    }

    public static Uploader getUploaderFromUser() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Uploader: ");
        String uploaderInput = sc.next();
        return new UploaderImpl(uploaderInput);
    }

    public static Duration getDurationFromUser() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Duration (seconds): ");
                long durationSeconds = sc.nextLong();
                return Duration.ofSeconds(durationSeconds);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a long integer for duration in seconds.");
                sc.next(); // consume invalid input
            }
        }
    }

    public static BigDecimal getCostFromUser() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Cost: ");
                return sc.nextBigDecimal();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid decimal number for cost.");
                sc.next(); // consume invalid input
            }
        }
    }
}