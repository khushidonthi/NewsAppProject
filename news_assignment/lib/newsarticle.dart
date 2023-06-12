class NewsArt {
  String imgUrl;
  String newsHead;
  String newsDes;
  String newsCnt;
  String newsUrl;

  NewsArt(
      {required this.imgUrl,
        required this.newsCnt,
        required this.newsDes,
        required this.newsHead,
        required this.newsUrl});

  static NewsArt fromAPItoApp(Map<String, dynamic> article) {
    return NewsArt(
        imgUrl: article["urlToImage"],
        newsCnt: article["content"] ,
        newsDes: article["description"] ,
        newsHead: article["title"],
        newsUrl: article["url"]);
  }
}