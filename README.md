# A web application testing framework example based on Selenium and TestNG.

## Disclaimer
**This code is provided "AS IS" without any guarantees of functionality. It is important to note that web element locators may have been altered since the creation of this framework, which was originally developed as part of a job interview process task. Users should be aware that these changes could affect the code's performance and operability.**

## How to run
1. Build the executable jar file using the following command:
   `mvn clean install` or `mvn clean install -DskipTests`
2. Copy the created "uber" jar file **_qamission-web-example.jar_** from _target_ folder into _bin_ folder
3. Execute the tests using run_test.sh (on linux based computers) or using run_test.bat (on windows machines).

**Note: if you are working with a linux based system, you can run: `deploy.sh` command that combines the steps above.**
**For more information see more detailed description of the framework at https://qamission.com

## Framework Requirements
*Design & develop an automation framework and automate the scenario described below using Selenium in Java 11.
*Check in your code in GitHub/Bitbucket, or you can choose to send it as a Zip file for review.

## Steps to Automate

### Task 1
- **Goto**: [https://www.loblaws.ca/](https://www.loblaws.ca/)
- **Action**: Search for `apples` and sort the search results from highest price to lowest price.
- **Verify**: Confirm that the website has sorted the price correctly. You can load more results and check for all search results or can check for the first result search page.

### Task 2
- **Action**: Search for `apples` and use the Price Reduction option under Filter By > Promotion to sort the search by sale badges.
- **Verify**: Each product: The amount on the badge and the price reduction match, and that the price in kg is equivalent to the price in lbs.

### Task 3
- **Action**: As a new user to the site, add an item to your cart.
- **Verify**: Item was added successfully. The site should ask you to pick a store, so please perform all necessary operations which will allow you to add an item into the cart once the user is created & logged in to the user account.

### Task 4
- **Action**: From the home page, click on “Change Location” or “Choose a location” if selecting location for the first time, search for “QUEEN STREET WEST” and select it for pickup. Go to location details by clicking the link present on the same page.
- **Verify**: It is displaying correct store locations details.
- **Continuation**: From the above step, now click on “View store Flyer >” link.
- **Verify**: The page is displaying correct store flyer details.
