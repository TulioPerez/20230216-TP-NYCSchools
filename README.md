# **20230216-TP-NYCSchools**
# NYC OpenData SAT Scores

#### **Overview:**
###### This application displays a NYC school list and corresponding average SAT scores for Critical Reading, Math and Writing.

It was built using Java with an MVVM pattern utilizing Retrofit for fetching data from the NYC Open Data API.

Its main components include:
	+ Two activities: ActivitySchoolList: the launcher activity that presents a list of NYC schools to the user
			ActivitySatScores: the detail view for a school that was clicked
	+ AdapterSchoolList: RecylerView adapter for ActivitySchoolList
	+ Repository classes: RepoSchools & RepoSATScores
	+ Model Classes: ModelSchools & ModelSATScores
	+ ViewModels: ViewModelSchoolList & ViewModelSATScores
(class types precede names for ease in finding in the project panel)

The application has a clear and intuitive interface that uses RecyclerView to display a list of schools with row banding to aid in distinguishing the elements in each row. 

Error handling functions exist to present error messages and allow re-fetching of data if an error occurs during this process by utilizing SwipeToRefresh.

Future additions would include:
	+ Test cases
	+ Splash screen with NYC Open Data logo
	+ Additional activity with school's contact details and description
	+ "Compare" view with side by side results of selected schools' SAT school scores
	+ A SearchView search bar to find schools more quickly by name or location
	+ String translations of descriptions, etc., for speakers of other languages who may be interested in attending a particular school
