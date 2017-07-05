/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooring.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author apprentice
 */
public class UserIOConsoleImpl implements UserIO {

    Scanner myScanner = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        boolean isValidType = false;
        Double input = 0.0;

        while (!isValidType) {
            try {
                System.out.println(prompt);
                input = Double.parseDouble(myScanner.nextLine());
                isValidType = true;
            } catch (NumberFormatException e) {
                System.out.println("WARNING: You entered the wrong data type, please try again");
                isValidType = false;
            }
        }
        return input;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        Double userIn = 0.0;
        boolean isValidType = false;

        while (!isValidType) {
            try {
                userIn = this.readDouble(prompt);
                isValidType = true;
                while (userIn < min || userIn > max) {
                    System.out.println("WARNING: Your entry was not in bounds (" + min + " to " + max + "), please try again.");
                    userIn = this.readDouble(prompt);
                }

            } catch (NumberFormatException e) {
                System.out.println("WARNING: You entered the wrong data type, please try again");
                isValidType = false;
            }
        }
        return userIn;
    }

    @Override
    public float readFloat(String prompt) {
        boolean isValidType = false;
        Float input = 0f;

        while (!isValidType) {
            try {
                System.out.println(prompt);
                input = Float.parseFloat(myScanner.nextLine());
                isValidType = true;
            } catch (NumberFormatException e) {
                System.out.println("WARNING: You entered the wrong data type, please try again");
                isValidType = false;
            }
        }
        return input;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        Float userIn = 0f;
        boolean isValidType = false;

        while (!isValidType) {
            try {
                userIn = this.readFloat(prompt);
                isValidType = true;
                while (userIn < min || userIn > max) {
                    System.out.println("WARNING: Your entry was not in bounds (" + min + " to " + max + "), please try again.");
                    userIn = this.readFloat(prompt);
                }

            } catch (NumberFormatException e) {
                System.out.println("WARNING: You entered the wrong data type, please try again");
                isValidType = false;
            }
        }
        return userIn;
    }

    @Override
    public int readInt(String prompt) {
        boolean isValidType = false;
        Integer input = 0;

        while (!isValidType) {
            try {
                System.out.println(prompt);
                input = Integer.parseInt(myScanner.nextLine());
                isValidType = true;
            } catch (NumberFormatException e) {
                System.out.println("WARNING: You entered the wrong data type, please try again");
                isValidType = false;
            }
        }
        return input;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        Integer userIn = 0;
        boolean isValidType = false;

        while (!isValidType) {
            try {
                userIn = this.readInt(prompt);
                isValidType = true;
                while (userIn < min || userIn > max) {
                    System.out.println("WARNING: Your entry was not in bounds (" + min + " to " + max + "), please try again.");
                    userIn = this.readInt(prompt);
                }

            } catch (NumberFormatException e) {
                System.out.println("WARNING: You entered the wrong data type, please try again");
                isValidType = false;
            }
        }
        return userIn;
    }

    @Override
    public long readLong(String prompt) {
        boolean isValidType = false;
        Long input = 0l;

        while (!isValidType) {
            try {
                System.out.println(prompt);
                input = Long.parseLong(myScanner.nextLine());
                isValidType = true;
            } catch (NumberFormatException e) {
                System.out.println("WARNING: You entered the wrong data type, please try again");
                isValidType = false;
            }
        }
        return input;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        Long userIn = 0l;
        boolean isValidType = false;

        while (!isValidType) {
            try {
                userIn = this.readLong(prompt);
                isValidType = true;
                while (userIn < min || userIn > max) {
                    System.out.println("WARNING: Your entry was not in bounds (" + min + " to " + max + "), please try again.");
                    userIn = this.readLong(prompt);
                }

            } catch (NumberFormatException e) {
                System.out.println("WARNING: You entered the wrong data type, please try again");
                isValidType = false;
            }
        }
        return userIn;
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return myScanner.nextLine();
    }

    @Override
    public BigDecimal readUSCurrency(String prompt) {
        boolean isValidType = false;
        BigDecimal input = new BigDecimal("0.0");
        while (!isValidType) {
            try {
                System.out.println(prompt);
                input = new BigDecimal(myScanner.nextLine().replaceAll("[$,]", "")).setScale(2, RoundingMode.HALF_UP);
                isValidType = true;
            } catch (NumberFormatException e) {
                System.out.println("WARNING: Could not convert the format of your entry to US currency.  Please try again.");
                isValidType = false;
            }
        }
        return input;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        boolean isValidType = false;
        BigDecimal input = new BigDecimal("0.0");
        while (!isValidType) {
            try {
                System.out.println(prompt);
                input = new BigDecimal(myScanner.nextLine());
                isValidType = true;
            } catch (NumberFormatException e) {
                System.out.println("WARNING: Could not convert the format of your entry to a Big Decimal.  Please try again.");
                isValidType = false;
            }
        }
        return input;
    }

    @Override
    public BigDecimal readPositiveBigDecimal(String prompt) {
        boolean isValidType = false;
        BigDecimal input = new BigDecimal("0.0");
        while (!isValidType) {
            try {
                System.out.println(prompt);
                input = new BigDecimal(myScanner.nextLine());
                if (input.compareTo(BigDecimal.ZERO) < 0) {
                    this.print("Input must be greater than zero.");
                    isValidType = false;
                } else {
                    //inputStr = input.toString();
                    isValidType = true;
                }

                //isValidType = true;
            } catch (NumberFormatException e) {
                System.out.println("WARNING: Could not convert the format of your entry to a Big Decimal.  Please try again.");
                isValidType = false;
            }
        }
        return input;
    }

    @Override
    public LocalDate readLocalDate(String prompt) {
        boolean isValidType = false;
        LocalDate input = LocalDate.now();
        while (!isValidType) {
            try {
                System.out.println(prompt);
                input = LocalDate.parse(myScanner.nextLine(), DateTimeFormatter.ofPattern("MM-dd-yyyy"));
                isValidType = true;
            } catch (DateTimeParseException e) {
                System.out.println("WARNING: Please enter your local date in the following format: MM-DD-YYYY");
                isValidType = false;
            }
        }
        return input;
    }

    @Override
    public String readLocalDateEditMode(String prompt) {
        //READ LOCAL DATE BUT RETURN STRING
        boolean isValidType = false;
        String inputStr = "";
        LocalDate input = LocalDate.now();
        while (!isValidType) {
            try {
                inputStr = this.readString(prompt);
                if (inputStr.equals("")) {
                    isValidType = true;
                } else {
                    input = LocalDate.parse(inputStr, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
                    inputStr = input.toString();
                    isValidType = true;
                }
            } catch (DateTimeParseException e) {
                System.out.println("WARNING: Please enter your local date in the following format: MM-DD-YYYY");
                isValidType = false;
            }
        }
        return inputStr;
    }

    @Override
    public String readBigDecimalEditMode(String prompt) {
        //READS BIG DECIMAL BUT RETURNS STRING
        boolean isValidType = false;
        String inputStr = "";
        BigDecimal input = new BigDecimal("0.0");

        while (!isValidType) {
            try {
                inputStr = this.readString(prompt);
                if (inputStr.equals("")) {
                    isValidType = true;
                } else {
                    input = new BigDecimal(inputStr.replaceAll("[$,%]", "")).setScale(2, RoundingMode.HALF_UP);
                    //CHECK THAT ITS GREATER THAN 0
                    if (input.compareTo(BigDecimal.ZERO) < 0) {
                        this.print("Input must be greater than zero.");
                        isValidType = false;
                    } else {
                        inputStr = input.toString();
                        isValidType = true;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("WARNING: Could not convert the format of your entry to a Big Decimal.  Please try again.");
                isValidType = false;
            }
        }
        return inputStr;
    }
}
