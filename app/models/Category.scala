package models

//import org.joda.time.DateTime
//import play.api.libs.json.{Format, Json}
//import reactivemongo.play.json._
//import reactivemongo.bson.BSONObjectID
//import reactivemongo.bson._
//import play.api.libs.json.JodaWrites._
//import play.api.libs.json.JodaReads._

import play.api.libs.json.{Format, Json}
import play.modules.reactivemongo._
import reactivemongo.bson._
import reactivemongo.play.json._

case class Category (
                    _id: Option[BSONObjectID],
                    title: String,
                    description: String,
                    items: Array[BSONObjectID]
                    )
object Category {
  implicit val categoryFormat: Format[Category] = Json.format[Category]

  implicit val categoryWriter: BSONDocumentWriter[Category] = Macros.writer[Category]
  implicit val categoryReader: BSONDocumentReader[Category] = Macros.reader[Category]

//  implicit object categoryBSONReader extends BSONDocumentReader[Category] {
//    def read(doc: BSONDocument): Category = {
//      Category(
//        doc.getAs[BSONObjectID]("_id"),
//        doc.getAs[String]("name").get,
//        doc.getAs[String]("description").get,
//        doc.getAs[Array[BSONObjectID]]("items").get
//      )
//    }
//  }
//
//  implicit object categoryBSONWriter extends BSONDocumentWriter[Category] {
//    def write(category: Category): BSONDocument = {
//      BSONDocument(
//        "_id" -> category._id,
//        "title" -> category.title,
//        "description" -> category.description,
//        "items" -> category.items
//      )
//    }
//  }
}
