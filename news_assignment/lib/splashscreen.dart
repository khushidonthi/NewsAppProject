import 'package:flutter/material.dart';
import 'news.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';

class Splash extends StatefulWidget {
  const Splash({Key? key}) : super(key: key);

  @override
  State<Splash> createState() => _SplashState();
}

class _SplashState extends State<Splash> {
  @override
  void initState(){
    super.initState();
    _navigatetopage();
  }

  _navigatetopage() async{
    await Future.delayed(Duration(milliseconds: 3500), (){});
    Navigator.pushReplacement(context, MaterialPageRoute(builder: (context)=>HomeScreen()));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Colors.red[900],
      body: Center(
        child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [Icon(Icons.incomplete_circle, color: Colors.white,size:100),
          SizedBox(height: 30,),
          Text('Loading....', style: TextStyle(color: Colors.white, fontSize: 30))]),
      )
    );
  }
}
