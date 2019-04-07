# movieParser
simple application which impliments next requarements 
As a user I can login to Facebook;
As a user I can see my userpic preview;
As a user I see the list of ongoing movies for 3 months period, grouped by
release months(as categories in list) and sorted by rating inside each group;
As a user I can refresh the list of movies using pull-to-refresh;
As a user I view the list of movies without internet connection;
As a user I can add or remove each movie to bookm$rk (all changes should
become visible instantly);
As a user I can share each movie info (via any provider);
As a user I can see the list of my bookmarked movies;
Each data-bound operation should have one of these states at UI:
-empty state
-in progress
-failed(with reason: i.e. connectivity/ server issue);
-success (data shown);

Resources
The movie database API: https://www.themoviedb.org/documentation/api/
discover
