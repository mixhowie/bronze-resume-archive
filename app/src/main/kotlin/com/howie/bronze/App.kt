package com.howie.bronze

import notion.api.v1.NotionClient
import notion.api.v1.model.pages.PageParent
import notion.api.v1.model.pages.PageProperty

fun main() {
    val token = System.getenv("BRONZE_NOTION_TOKEN")
    val databaseId = System.getenv("BRONZE_NOTION_DATABASE_ID")
    val notion = NotionClient(
        token = System.getenv("NOTION_TOKEN")
    )
    val database = notion.retrieveDatabase(databaseId)
    notion.createPage(
        parent = PageParent.database(databaseId),
        properties = mapOf(
            "Name" to PageProperty(title = "Create From Bronze".asRichText())
        )
    )
}

private fun String.asRichText(): List<PageProperty.RichText> =
    listOf(PageProperty.RichText(text = PageProperty.RichText.Text(content = this)))