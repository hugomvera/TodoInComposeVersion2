package com.blacksnowymanx.Navigation

import androidx.compose.runtime.livedata.observeAsState
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.blacksnowymanx.todoincomposeversion2.roomListNames.ListName
import com.blacksnowymanx.todoincomposeversion2.roomListNames.ListNameViewModel
import com.blacksnowymanx.todoincomposeversion2.viewmodel.TaskViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.blacksnowymanx.todoincomposeversion2.room.Task

import kotlin.toString

//TODO need to comment
@Composable
fun DetailScreen(
    navController: NavHostController,
    taskViewModel: TaskViewModel,
    listNameViewModel: ListNameViewModel,
    id: Int
){

    //val name by listNameViewModel.getById(id).observeAsState("")
    //TOdo need to add an obversvable here
// var name =""
//    Text(
//        text = name,
//        modifier = Modifier.padding(bottom = 16.dp),
//        // You can style this however you want:
//        // fontSize = MaterialTheme.typography.headlineMedium.fontSize,
//        // fontWeight = FontWeight.Bold
//    )

    //TODO might want to add a go back button and could reuse the code below
    //TODO need to add a way to  query the list name  and display it on the top



//    Box(
//       modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ){
//        Text(
//
//            modifier = Modifier.clickable {
//                navController.navigate(Screen.Home.route)
//                {popUpTo(Screen.Home.route)
//                {
//                    inclusive = true
//                } }
//                //navController.popBackStack()
//            },
//            text = "Deetail",
//            color = Color.Red,
//            //fontSize = MaterialTheme.typography.h3.fontSize,
//            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
//            fontWeight = FontWeight.Bold
//        )
//    }
    //this is where the boiler code stops

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        Toast.makeText(context, "ID passed: $id", Toast.LENGTH_SHORT).show()
    }






    Greeting(
        name = "Android",
        modifier = Modifier.padding(15.dp),
        taskViewModel,
        listNameViewModel,
        id
    )



}













@Composable
fun Greeting(name: String, modifier: Modifier = Modifier,taskViewModel: TaskViewModel,listNameViewModel:ListNameViewModel,id: Int) {
    val context = LocalContext.current


    // Observe the LiveData
    //val listNames  by taskViewModel.listgetAllListName.observeAsState()
    //val listName by listNameViewModel.allListNames.observeAsState(emptyList()).toString()
    // Observe the LiveData correctly
    val listName by listNameViewModel.getById(id).observeAsState("Loading...")

    val taskList by taskViewModel.allTasks.observeAsState(emptyList())

    var counter = remember { mutableIntStateOf(0) }
    var text = remember { mutableStateOf("") }



    //let us do an observer here



    //this alone will cause an error
    // i cannot just print all of the thi ngs in the tasklist
    //Log.d("TaskApp", taskViewModel.allTasks.toString())


    val padding = 25.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(30.dp))



        Text(listName, fontSize = 30.sp)
// Replace "Loading name..." with an appropriate placeholder or handle the null case

        Row(){
            OutlinedTextField(
                value = text.value,
                onValueChange = { text.value = it },
                label = { Text("Enter your Task") },
                placeholder = { Text("Task ...") },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)

            )



            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = {




                //when; it is not empty then add it to the list

//                if(text.value.isNotBlank()){
//                    taskViewModel.insert(taskTest)
//                }else {
//                    Toast.makeText(context, "Task Cannot be Empty", Toast.LENGTH_SHORT).show()
//                }
//


                //TODO There somekind of error where if there is a
                //Todo Add change to diffierent langauages i guyess
                //Todo the input line width
                //TODo ADD navigation





            }) {
                Text("Add")
            }



        }



        //this is where once things have been inputed on the input field then it will reiterate though them and list them

        Log.d("Testing", "starting loop")

//        LazyColumn {
//            items(taskList){ item -> TaskCard(item,
//                onCheckedChange = {
//
//                    //will update task here to show that the
//                    //task was completed or not
//                    //this is working and will update the database
//                    //however it will not update on the screen itself
//                    //lets see if we can fix that
//                    //I need to modify the tasklist
//
//                    //item.isCompleted = !item.isCompleted
//                    var checkedOrNot = !item.isCompleted
//                    var t1 = Task(
//                        //will let room auto generate the id or not
//                        id = item.id,
//                        title = "Todo 1",
//                        //description = item.description,
//                        isCompleted = checkedOrNot)
//                    taskViewModel.update(t1)
//                },
//                onThrashCancle = {
//                    taskList.minus(item)
//                    //will delete from the databse itself
//                    taskViewModel.delete(item)
//                }) }
//        }


    }






}

@Composable
fun TaskCard(task: Task, onCheckedChange: (Boolean) -> Unit = {}, onThrashCancle: () -> Unit = {}) {

    //TODO add a box outline for every row



    //TODO if it is checked then strike thorugh the text

    //TODO maybe look at other apps for styling
    //add space between the rows
    Spacer(modifier = Modifier.height(10.dp))


//
//    var isToggled by rememberSaveable {
//        mutableStateOf(task.isCompleted)
//    }

    Row(modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically

    ) {
        //checkcbox or box depedning if is is checked or not


        //this icon is the checkbox
        //so if we click it then we will change the value of ischecked

        //TODO restrict the lenght of a todo itiem
        //TODO restrict length of input
        IconButton(
            modifier = Modifier.fillMaxWidth(.1f),

            onClick = {

//                isToggled = !isToggled
//                onCheckedChange(isToggled)
            }
        ) {
//            Icon(
//                modifier = Modifier.fillMaxWidth(.5f),
//                painter = if (
//                    isToggled
//                ) painterResource(id = R.drawable.baseline_check_box_24) else painterResource(id = R.drawable.baseline_check_box_outline_blank_24),
//                contentDescription = null // decorative element,
//
//            )
        }

        //text with task title
       // Text(  modifier = Modifier.fillMaxWidth(.891f),text = task.description)



        IconButton(
            modifier = Modifier.fillMaxWidth(.5f),
            onClick = { onThrashCancle()
            }
        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.baseline_cancel_24),
//                contentDescription = null // decorative element
//            )
        }

        //ad some space in the rows
        //center the text
        //delete button should be all the way to the righth
        //Maybe make the text bigger once seen in a real device

        //delete icon or button

        //TODO rename application
        //TODO make a better name for the app
        //TODO navigation


    }

}

//Todo some hoisting to change the task
//level 10
//Todo Fix the button so it is not to big vertical whise

//TODO I want to be able when the device is fliped on itside then the input will take the whole



