// https://newsapi.org/v2/top-headlines?sources=google-news-in&apiKey=9bb7bf6152d147ad8ba14cd0e7452f2f
import 'dart:convert';
import 'dart:math';
import 'package:http/http.dart';
import 'newsarticle.dart';

class FetchNews {

  static Future<NewsArt> fetchNews() async {

    Response response = await get(Uri.parse(
        "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=2caf6039aee9433b80994f134e3bee6b"));

    Map body_data = jsonDecode(response.body);
    List articles = body_data["articles"];

    final _random = new Random();
    var myArticle = articles[_random.nextInt(articles.length)];


    return NewsArt.fromAPItoApp(myArticle);
  }
}