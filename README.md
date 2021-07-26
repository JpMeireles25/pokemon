# pokemon

Simple pokemon app with mvvm design pattern that uses retrofit to call an api with pokemon data. All the calls are dealt in the repository, no database for now.  But Since its an api with a large amount of data. its always a good practise to store some info. For now, I implemented a recycler view with a paging adapter (using paging 3 lib) that allows to load the info from all pokemons. if you click on a item from the list it will open a new fragment with info from that pokemon.  all info is pass from the View model to the views using livedata. Also implemented a search bar to find a pokemon directyl. (To improve it would be better to add a filter for the recycler view).
After being on the details page it allows you to mark the pokemon has favourite.
Link to check the POST Call
 https://webhook.site/#!/6f261184-484f-40e6-a35f-26716dcd3fd0/aa0db83f-68e7-4696-9985-accbf81baa9d/1
