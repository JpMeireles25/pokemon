# pokemon

Simple pokemon app with mvvm design pattern that uses retrofit to call an api with pokemon data. All the calls are made in the repository, no database for now.  But Since its an api with a large amount of data. its always a good practise to store info locally. For now, to help with that  implemented a recycler view with a paging adapter (ussing paging 3 lib) that always to load the info from all pokemons. if you click on a item of the list it will open a new fragment with info from that pokemon.  all info is pass from the vm to views using livedata. also implemented a search bar to find a pokemon directyl. to improve it would be better to add a filter for the recycler view.
After being on the details page it allows you to mark the pokemon has favourite.
 https://webhook.site/#!/6f261184-484f-40e6-a35f-26716dcd3fd0/aa0db83f-68e7-4696-9985-accbf81baa9d/1
