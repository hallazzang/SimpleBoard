package simpleboard.common;

import java.io.Serializable;

public class Paginator implements Serializable {
    private String boardId;
    private int page, totalCount;
    private int itemsPerPage, pageNavCount;

    public Paginator(String boardId, int page, int totalCount, int itemsPerPage, int pageNavCount) {
        super();
        this.boardId = boardId;
        this.page = page;
        this.totalCount = totalCount;
        this.itemsPerPage = itemsPerPage;
        this.pageNavCount = pageNavCount;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public int getPageNavCount() {
        return pageNavCount;
    }

    public void setPageNavCount(int pageNavCount) {
        this.pageNavCount = pageNavCount;
    }

    public String render() {
        int pages = (int) Math.ceil((double)totalCount / itemsPerPage);
        int left = ((int) Math.ceil((double)page / pageNavCount) - 1) * pageNavCount + 1;
        int right = Math.min(left + pageNavCount - 1, pages);
        String result = "";

        if (left > 1) {
            result += "<a href=\"/board?boardId=" + boardId + "&page=" + (left - 1) + "\">&laquo;</a>";
        } else {
            result += "<a>&laquo;</a>";
        }

        for (int p = left; p <= right; p++) {
            if (p == page) {
                result += "<a>" + p + "</a>";
            } else {
                result += "<a href=\"/board?boardId=" + boardId + "&page=" + p + "\">" + p + "</a>";
            }
        }

        if (right < pages) {
            result += "<a href=\"/board?boardId=" + boardId + "&page=" + (right + 1) + "\">&raquo;</a>";
        } else {
            result += "<a>&raquo;</a>";
        }

        return result;
    }
}
