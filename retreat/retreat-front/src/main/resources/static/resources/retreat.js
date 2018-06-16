//retreat 공통함수

//숫자형 변환
function numberFormat(num) {

	if (num == 0)
		return 0;

	var reg = /(^[+-]?\d+)(\d{3})/;
	var n = (num + '');

	while (reg.test(n)){
		n = n.replace(reg, '$1' + ',' + '$2');
	}

	return n;

}
