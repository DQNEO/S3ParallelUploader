package jp.dqneo.amazons3;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import jp.dqneo.amazons3.UploadableFile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.S3ResponseMetadata;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.BucketLoggingConfiguration;
import com.amazonaws.services.s3.model.BucketNotificationConfiguration;
import com.amazonaws.services.s3.model.BucketPolicy;
import com.amazonaws.services.s3.model.BucketTaggingConfiguration;
import com.amazonaws.services.s3.model.BucketVersioningConfiguration;
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.CopyPartRequest;
import com.amazonaws.services.s3.model.CopyPartResult;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.DeleteBucketPolicyRequest;
import com.amazonaws.services.s3.model.DeleteBucketRequest;
import com.amazonaws.services.s3.model.DeleteBucketWebsiteConfigurationRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.DeleteVersionRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetBucketAclRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.GetBucketPolicyRequest;
import com.amazonaws.services.s3.model.GetBucketWebsiteConfigurationRequest;
import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ListBucketsRequest;
import com.amazonaws.services.s3.model.ListMultipartUploadsRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ListPartsRequest;
import com.amazonaws.services.s3.model.ListVersionsRequest;
import com.amazonaws.services.s3.model.MultipartUploadListing;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.PartListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.Region;
import com.amazonaws.services.s3.model.RestoreObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.SetBucketAclRequest;
import com.amazonaws.services.s3.model.SetBucketLoggingConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketPolicyRequest;
import com.amazonaws.services.s3.model.SetBucketVersioningConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketWebsiteConfigurationRequest;
import com.amazonaws.services.s3.model.StorageClass;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;
import com.amazonaws.services.s3.model.VersionListing;

public class UploadableFileTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        UploadableFile myFile;

        myFile = new UploadableFile(null, new File("c:\\tmp\basedir"), new File("c:\\tmp\basedir\\foo\\file.txt"),"mybucket",null);
        assertThat(myFile.getS3Key(), is("foo/file.txt"));

        myFile = new UploadableFile(null, new File("c:\\tmp\basedir"), new File("c:\\tmp\basedir\\bar\\file.txt"),"mybucket",null);
        assertThat(myFile.getS3Key(), is("bar/file.txt"));

        myFile = new UploadableFile(null, new File("c:\\tmp\basedir"), new File("c:\\tmp\basedir\\file.txt"),"mybucket",null);
        assertThat(myFile.getS3Key(), is("file.txt"));

        myFile = new UploadableFile(null, new File("c:\\tmp\basedir"), new File("c:\\tmp\basedir\\file.txt"),"mybucket", "target");
        assertThat(myFile.getS3Key(), is("target/file.txt"));
}

    @Test
    public void upload() {
        FakeAmazonS3 fakeAmazonS3 = new FakeAmazonS3();
        UploadableFile uploadableFile = new UploadableFile(fakeAmazonS3, new File("c:\\tmp\basedir"), new File("c:\\tmp\basedir\\foo\\file.txt"),null,null);
        assertThat(fakeAmazonS3.hasPutObjectRequest, is(false));

        uploadableFile.upload();
        assertThat(fakeAmazonS3.hasPutObjectRequest, is(true));
    }

}

class FakeAmazonS3 implements AmazonS3 {

    public boolean hasPutObjectRequest = false;

    
    public PutObjectResult putObject(PutObjectRequest putObjectRequest)
            throws AmazonClientException, AmazonServiceException {
        hasPutObjectRequest = true;
        return null;
    }

    
    public void setEndpoint(String endpoint) {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public void changeObjectStorageClass(String bucketName, String key,
            StorageClass newStorageClass) throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public void setObjectRedirectLocation(String bucketName, String key,
            String newRedirectLocation) throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public ObjectListing listObjects(String bucketName)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public ObjectListing listObjects(String bucketName, String prefix)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public ObjectListing listObjects(ListObjectsRequest listObjectsRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public ObjectListing listNextBatchOfObjects(
            ObjectListing previousObjectListing) throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public VersionListing listVersions(String bucketName, String prefix)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public VersionListing listNextBatchOfVersions(
            VersionListing previousVersionListing)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public VersionListing listVersions(String bucketName, String prefix,
            String keyMarker, String versionIdMarker, String delimiter,
            Integer maxResults) throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public VersionListing listVersions(ListVersionsRequest listVersionsRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public Owner getS3AccountOwner() throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public boolean doesBucketExist(String bucketName)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

    
    public List<Bucket> listBuckets() throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public List<Bucket> listBuckets(ListBucketsRequest listBucketsRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public String getBucketLocation(String bucketName)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public String getBucketLocation(
            GetBucketLocationRequest getBucketLocationRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public Bucket createBucket(CreateBucketRequest createBucketRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public Bucket createBucket(String bucketName) throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public Bucket createBucket(String bucketName, Region region)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public Bucket createBucket(String bucketName, String region)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public AccessControlList getObjectAcl(String bucketName, String key)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public AccessControlList getObjectAcl(String bucketName, String key,
            String versionId) throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public void setObjectAcl(String bucketName, String key,
            AccessControlList acl) throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public void setObjectAcl(String bucketName, String key,
            CannedAccessControlList acl) throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public void setObjectAcl(String bucketName, String key, String versionId,
            AccessControlList acl) throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public void setObjectAcl(String bucketName, String key, String versionId,
            CannedAccessControlList acl) throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public AccessControlList getBucketAcl(String bucketName)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public void setBucketAcl(SetBucketAclRequest setBucketAclRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public AccessControlList getBucketAcl(
            GetBucketAclRequest getBucketAclRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public void setBucketAcl(String bucketName, AccessControlList acl)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public void setBucketAcl(String bucketName, CannedAccessControlList acl)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public ObjectMetadata getObjectMetadata(String bucketName, String key)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public ObjectMetadata getObjectMetadata(
            GetObjectMetadataRequest getObjectMetadataRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public S3Object getObject(String bucketName, String key)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public S3Object getObject(GetObjectRequest getObjectRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public ObjectMetadata getObject(GetObjectRequest getObjectRequest,
            File destinationFile) throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public void deleteBucket(DeleteBucketRequest deleteBucketRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public void deleteBucket(String bucketName) throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }


    
    public PutObjectResult putObject(String bucketName, String key, File file)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public PutObjectResult putObject(String bucketName, String key,
            InputStream input, ObjectMetadata metadata)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public CopyObjectResult copyObject(String sourceBucketName,
            String sourceKey, String destinationBucketName,
            String destinationKey) throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public CopyObjectResult copyObject(CopyObjectRequest copyObjectRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public CopyPartResult copyPart(CopyPartRequest copyPartRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public void deleteObject(String bucketName, String key)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public void deleteObject(DeleteObjectRequest deleteObjectRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public DeleteObjectsResult deleteObjects(
            DeleteObjectsRequest deleteObjectsRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public void deleteVersion(String bucketName, String key, String versionId)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public void deleteVersion(DeleteVersionRequest deleteVersionRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public BucketLoggingConfiguration getBucketLoggingConfiguration(
            String bucketName) throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public void setBucketLoggingConfiguration(
            SetBucketLoggingConfigurationRequest setBucketLoggingConfigurationRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public BucketVersioningConfiguration getBucketVersioningConfiguration(
            String bucketName) throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public void setBucketVersioningConfiguration(
            SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public BucketLifecycleConfiguration getBucketLifecycleConfiguration(
            String bucketName) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public void setBucketLifecycleConfiguration(String bucketName,
            BucketLifecycleConfiguration bucketLifecycleConfiguration) {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public void deleteBucketLifecycleConfiguration(String bucketName) {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public BucketCrossOriginConfiguration getBucketCrossOriginConfiguration(
            String bucketName) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public void setBucketCrossOriginConfiguration(String bucketName,
            BucketCrossOriginConfiguration bucketCrossOriginConfiguration) {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public void deleteBucketCrossOriginConfiguration(String bucketName) {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public BucketTaggingConfiguration getBucketTaggingConfiguration(
            String bucketName) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public void setBucketTaggingConfiguration(String bucketName,
            BucketTaggingConfiguration bucketTaggingConfiguration) {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public void deleteBucketTaggingConfiguration(String bucketName) {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public BucketNotificationConfiguration getBucketNotificationConfiguration(
            String bucketName) throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public void setBucketNotificationConfiguration(String bucketName,
            BucketNotificationConfiguration bucketNotificationConfiguration)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public BucketWebsiteConfiguration getBucketWebsiteConfiguration(
            String bucketName) throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public BucketWebsiteConfiguration getBucketWebsiteConfiguration(
            GetBucketWebsiteConfigurationRequest getBucketWebsiteConfigurationRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public void setBucketWebsiteConfiguration(String bucketName,
            BucketWebsiteConfiguration configuration)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public void setBucketWebsiteConfiguration(
            SetBucketWebsiteConfigurationRequest setBucketWebsiteConfigurationRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public void deleteBucketWebsiteConfiguration(String bucketName)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public void deleteBucketWebsiteConfiguration(
            DeleteBucketWebsiteConfigurationRequest deleteBucketWebsiteConfigurationRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public BucketPolicy getBucketPolicy(String bucketName)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public BucketPolicy getBucketPolicy(
            GetBucketPolicyRequest getBucketPolicyRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public void setBucketPolicy(String bucketName, String policyText)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public void setBucketPolicy(SetBucketPolicyRequest setBucketPolicyRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public void deleteBucketPolicy(String bucketName)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public void deleteBucketPolicy(
            DeleteBucketPolicyRequest deleteBucketPolicyRequest)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public URL generatePresignedUrl(String bucketName, String key,
            Date expiration) throws AmazonClientException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public URL generatePresignedUrl(String bucketName, String key,
            Date expiration, HttpMethod method) throws AmazonClientException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public URL generatePresignedUrl(
            GeneratePresignedUrlRequest generatePresignedUrlRequest)
            throws AmazonClientException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public InitiateMultipartUploadResult initiateMultipartUpload(
            InitiateMultipartUploadRequest request)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public UploadPartResult uploadPart(UploadPartRequest request)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public PartListing listParts(ListPartsRequest request)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public void abortMultipartUpload(AbortMultipartUploadRequest request)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ

    }

    
    public CompleteMultipartUploadResult completeMultipartUpload(
            CompleteMultipartUploadRequest request)
            throws AmazonClientException, AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public MultipartUploadListing listMultipartUploads(
            ListMultipartUploadsRequest request) throws AmazonClientException,
            AmazonServiceException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    
    public S3ResponseMetadata getCachedResponseMetadata(
            AmazonWebServiceRequest request) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

	
	public void setRegion(com.amazonaws.regions.Region region) throws IllegalArgumentException {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	
	public void setS3ClientOptions(S3ClientOptions clientOptions) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	
	public void restoreObject(RestoreObjectRequest copyGlacierObjectRequest) throws AmazonServiceException {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	
	public void restoreObject(String bucketName, String key, int expirationInDays) throws AmazonServiceException {
		// TODO 自動生成されたメソッド・スタブ
		
	}


}