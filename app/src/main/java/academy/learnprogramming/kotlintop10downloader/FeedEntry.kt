package academy.learnprogramming.kotlintop10downloader

class FeedEntry {
    var name=""
    var artist=""
    var releaseDate=""
    var summary=""
    var imageURL=""

    override fun toString(): String {
        return "name=$name artist=$artist releaseDate=$releaseDate imageURL=$imageURL"
    }
}
