//package com.yuan;
//
//import dev.langchain4j.store.embedding.pinecone.PineconeServerlessIndexConfig;
//import io.pinecone.clients.Index;
//import io.pinecone.clients.Pinecone;
//import org.openapitools.db_control.client.ApiException;
//import org.openapitools.db_control.client.model.CreateIndexForModelRequest;
//import org.openapitools.db_control.client.model.CreateIndexForModelRequestEmbed;
//import org.openapitools.db_control.client.model.DeletionProtection;
//import org.openapitools.db_control.client.model.IndexModel;
//import org.openapitools.db_data.client.model.SearchRecordsRequestQuery;
//import org.openapitools.db_data.client.model.SearchRecordsResponse;
//import io.pinecone.proto.DescribeIndexStatsResponse;
//
//import javax.sound.midi.Soundbank;
//import java.util.*;
//
///**
// * 测试Pinecone连接
// */
//public class Quickstart {
//    public static void main(String[] args) throws ApiException {
//        Pinecone pc = new Pinecone.Builder("pcsk_6jMiSp_P76ufQJrrtbXDkekHcNfH4ahTbgXQVuedN6N4XSr4WhaxV4dDDDkePZiN3NJSUt").build();
//        System.out.println(pc.listIndexes());
//    }
//}