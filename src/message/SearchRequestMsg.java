package message;

/**
 * 发送搜索请求，返回搜索结果
 * 如果搜索内容为空，则返回推荐内容
 *
 * @author : AkashiSensei
 * &#064;date  : 2022/12/8 20:21
 */
public class SearchRequestMsg extends BaseMsg{
    private final String searchText;
    private final int offset;
    private final int count;

    private SearchRequestMsg(String searchText, int offset, int count) {
        super(SEARCH_ARCHIVE);
        this.searchText = searchText;
        this.offset = offset;
        this.count = count;
    }

    /**
     * 创建SearchRequestMsg实例
     * @param searchText 搜索内容
     * @param offset 表示返回结果在排序中跳过的数量，例如offset的值为0，表示直接返回前几个结果，如果为10，则从第11个结果开始返回
     * @param count 最大返回的结果数量，即一页能够显示的数目
     * @return 返回新建的实例
     */
    public static SearchRequestMsg createSearchRequestMsg(String searchText, int offset, int count) {
        return new SearchRequestMsg(searchText, offset, count);
    }

    public String getSearchText() {
        return searchText;
    }

    public int getOffset() {
        return offset;
    }

    public int getCount() {
        return count;
    }
}
