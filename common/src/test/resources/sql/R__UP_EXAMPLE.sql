-- =============================================
-- 작성자 : 박준혁
-- 작성일 : 2024-09-27
-- 설 명  : 테스트용 프로시저
-- =============================================
CREATE PROCEDURE [dbo].[UP_EXAMPLE]           @QUERY_TYPE NCHAR(2),
                                              @EXAM_TYPE_CODE INT = NULL,
                                              @EXAM_DIV NCHAR(2) = NULL,
                                              @EDU_STEP_CODE NCHAR(2) = NULL
AS
BEGIN
    SET NOCOUNT ON;
    SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED

    DECLARE @ERROR_MESSAGE NVARCHAR(MAX);

    -- 테스트 조회
    IF @QUERY_TYPE = 'L1'
        BEGIN
            BEGIN TRY
                SELECT 1
            END TRY
            BEGIN CATCH
                SET @ERROR_MESSAGE = ERROR_MESSAGE();
                RAISERROR (@ERROR_MESSAGE, 15, 1);
            END CATCH

        END
END;
