package dao.utils;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;


public final class IKTokenizer4Lucene9 extends Tokenizer {

    // IK�ִ���ʵ��
    private final IKSegmenter _IKImplement;
    // ��Ԫ�ı�����
    private final CharTermAttribute termAtt;

    // ��Ԫλ������
    private final OffsetAttribute offsetAtt;

    // ��Ԫ��������
    private final TypeAttribute typeAtt;
    // ��¼���һ����Ԫ����λ��
    private int endPosition;

    /**
     * @param useSmart
     */
    public IKTokenizer4Lucene9(boolean useSmart) {
        super();
        offsetAtt = addAttribute(OffsetAttribute.class);
        termAtt = addAttribute(CharTermAttribute.class);
        typeAtt = addAttribute(TypeAttribute.class);
        _IKImplement = new IKSegmenter(input, useSmart);
    }


    @Override
    public boolean incrementToken() throws IOException {
        // ������д�Ԫ����
        clearAttributes();
        Lexeme nextLexeme = _IKImplement.next();
        if (nextLexeme != null) {
            // ��Lexemeת��Attributes
            // ���ô�Ԫ����
            termAtt.append(nextLexeme.getLexemeText());
            // ���ô�Ԫ����
            termAtt.setLength(nextLexeme.getLength());
            // ���ô�ԪΨһ
            offsetAtt.setOffset(nextLexeme.getBeginPosition(), nextLexeme.getEndPosition());
            // ��¼�ִʵ����λ��
            endPosition = nextLexeme.getEndPosition();
            // ��¼��Ԫ����
            typeAtt.setType(nextLexeme.getLexemeTypeString());
            // ����ture��֪�����¸���Ԫ
            return true;
        }
        // ����false��֪������
        return false;
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        _IKImplement.reset(input);
    }

    @Override
    public void end() throws IOException {
        super.end();
        int finalOffset = correctOffset(this.endPosition);
        offsetAtt.setOffset(finalOffset, finalOffset);
    }
}
