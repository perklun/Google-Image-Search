# Googe Image Search

Description

A simple Android application that searches for pictures using the Google Image Search API based on a provided query. It also allows user to click on "settings" in the menuitem and select advanced filters such as size, color, type and site. Filters are persistent during the app session. When thumbnails are clicked, the full image is displayed with the aspect ratios maintained.

Technial Details
- Android Async is used to make HTTP requests to Google Image Search API 
- Images are retrieved by Picasso
- It has a custom onScrollListener to support "infinite" scrolling (up to a maximum of 64 pictures) 
- Added custom GridView adapter.

When thumbnails are clicked, the full image is displayed with the aspect ratios maintained.

Walkthrough of all user stories:

![](image_search.gif)
