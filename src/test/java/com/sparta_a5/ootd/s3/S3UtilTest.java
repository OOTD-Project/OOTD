package com.sparta_a5.ootd.s3;

import com.sparta_a5.ootd.common.s3.S3Const;
import com.sparta_a5.ootd.common.s3.S3Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class S3UtilTest {

    @Autowired
    private S3Util s3Util;

    @Nested
    @DisplayName("S3 유저 프로필 이미지 테스트")
    class S3UserProfileImageTest {

        @Test
        @DisplayName("S3 유저 프로필 이미지 테스트 업로드 성공")
        public void uploadImage_success() throws IOException {
            //given
            String originalFilename = "s3_test_image.png";
            String path = "src/test/resources/" + originalFilename;
            FileInputStream fileInputStream = new FileInputStream(path);
            MockMultipartFile file = new MockMultipartFile(
                    "image",
                    originalFilename,
                    "png",
                    fileInputStream
            );

            //when
            String filename = s3Util.uploadImage(S3Const.S3_DIR_USER_PROFILE, file);

            //then
            assertNotNull(filename);
        }

        @Test
        @DisplayName("S3 유저 프로필 이미지 테스트 삭제 성공")
        public void deleteImage_success() throws IOException{
            //given
            String originalFilename = "s3_test_image.png";
            String path = "src/test/resources/" + originalFilename;
            FileInputStream fileInputStream = new FileInputStream(path);
            MockMultipartFile file = new MockMultipartFile(
                    "image",
                    originalFilename,
                    "png",
                    fileInputStream
            );

            //when
            String filename = s3Util.uploadImage(S3Const.S3_DIR_USER_PROFILE, file);
            boolean removed = s3Util.deleteImage(S3Const.S3_DIR_USER_PROFILE, filename);

            //then
            assertTrue(removed);
        }

    }

}
