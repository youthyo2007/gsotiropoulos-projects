//CREATE PAGINATOR
function createHtml_PaginatorWithPageNumbers(paginator) {
	

	var pageNumbersList = paginator.pageNumbersList;
	
	
	$('.paginator').html("");
	var html = '';
	
	
			html = '<table style="border-collapse: separate; border-spacing: 2px;"><tr>';
			for (var i=0; i<pageNumbersList.length; i++) {
				if(pageNumbersList[i].pageno==(paginator.currentPage+1))
					html = html + '<td class="bg-info-dark" align="center" style="width: 30px;"><span class="text-bold">'+pageNumbersList[i].pageno+'</span></td>';
				else if((pageNumbersList[i].pageno=='AFTER_ONE'))
					html = html + '<td class="" align="center" style="width: 30px;"><span class="text-bold">&nbsp;&nbsp;&nbsp;</span></td>';
				else if((pageNumbersList[i].pageno=='DOTS'))
					html = html + '<td class="bg-gray" align="center" style="width: 30px;"><span class="text-bold">...</span></td>';
				else 
					html = html + '<td class="bg-gray" align="center" style="width: 30px;"><span class="text-bold"><a href="?page='+pageNumbersList[i].pageno+'">'+pageNumbersList[i].pageno+'</a></span></td>';
			}
			html = html +  '</tr></table>';

			$('.paginator').html(html);
	
}