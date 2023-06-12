import 'package:flutter/material.dart';
import 'package:news_assignment/splashscreen.dart';

void main() {
  runApp(
      MaterialApp(
      home: Scaffold(
        backgroundColor: Colors.red[900],
        body: HomePage(onTap:(){}),
        ),),);}

class HomePage extends StatelessWidget {
  HomePage({required this.onTap});
  final Function onTap;


  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Row( mainAxisAlignment: MainAxisAlignment.center,
            children: [Text(
              'News Reader  ',
              style: TextStyle(color: Colors.white, fontSize: 38.0)),
            Icon(Icons.newspaper_rounded, color: Colors.white,size:38)],),
          SizedBox(height: 10),

          Text(
              'Welcome Back!\n',
              style: TextStyle(color: Colors.white, fontSize: 25.0)),

          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 20),
            child: TextField(
              style: TextStyle(color: Colors.white, fontSize: 20),
              decoration: InputDecoration(
                  enabledBorder: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(10),
                    borderSide: BorderSide(color: Colors.white,),
                  ),
                  focusedBorder: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(10),
                    borderSide: BorderSide(color: Colors.white,),),
                  hintText: "Your Name",
                  hintStyle: TextStyle(color: Colors.white),
                  border: OutlineInputBorder(borderRadius: BorderRadius.circular(10),)),
            ),
          ),

          GestureDetector(onTap:(){
          Navigator.push(context, MaterialPageRoute(builder: (context)=>Splash()));
          },
            child: Container(
              padding: EdgeInsets.all(20),
              margin: EdgeInsets.symmetric(horizontal: 100),
              decoration: BoxDecoration(color: Colors.white, borderRadius: BorderRadius.circular(10)),
              child: Center(child: Text("Enter", style: TextStyle(color: Colors.red[900], fontSize: 22,fontWeight: FontWeight.bold)),),
            ),
          ),
        ],
      ),
    );
  }
}


