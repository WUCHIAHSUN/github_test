# Goal
List GitHub users.  
- [x] Please find out how to search or retrieve users from GitHub in [GitHub User API](https://docs.github.com/en/rest/reference/users).
- [x] Show users in list.
- [x] Limit results to 100 users in total, or you can choose to implement pagination feature (see details in Bonus section).
- [x] In list, each row should at least show info including image from “avatar_url”, “login”, and “site_admin”.
- [x] Implement with multithread (e.g. put time-consuming work in background thread, meanwhile your app should be responsive to user input events).
- [x] Implement in MVVM if possible (or explain for any other architecture of your choice).

# Bonus
Please feel free to ignore bonus items if there is no free time.
- [x] Implement pagination feature by utilizing [GitHub Pagination](https://docs.github.com/en/rest/guides/traversing-with-pagination), please start from "since=0, page size=20"
- [x] Upon clicking on an user in the list, open a page for a single user to show more detailed information. (GitHub account information API)
- Consider how to handle caching or connection issues to improve user experience.
- [x] Writing unit tests.
- Writing UI tests.

# Note
- Use Jetpack Compose Navigation.
- Update the user list using DiffCallback every time you scroll to the bottom.
- Use setFragmentResult to pass information between fragments.
- Use "io.coil-kt:coil:2.5.0" to load images.
- Use DataBinding + MutableLiveData.
- Use ExecutorService + Handler to update the UI from the API.

