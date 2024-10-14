/* ------------------------------------------------------------------------------- */
/*
    탭 박스 구조 예시

    <div class="tabBox_wrap">
        <div class="btn_wrap">
            <button class="btn active">1</button>
            <button class="btn">2</button>
        </div>
        <div class="tabBox">
            <div class="list_table_wrap">테이블1</div>
            <div class="list_table_wrap none">테이블2</div>
        </div>
    </div>
*/
const tabBox = document.querySelectorAll('.tabBox_wrap > .btn_wrap > .btn');
tabBox.forEach(button => {
    button.addEventListener('click', function() {
        const btnWrap = this.parentElement;
        const tabWrap = btnWrap.parentElement;
        const tabContainer = tabWrap.querySelector('.tabBox');

        // 모든 버튼에서 'active' 클래스 제거
        btnWrap.querySelectorAll('.btn').forEach(btn => btn.classList.remove('active'));

        // 클릭된 버튼에 'active' 클래스 추가
        this.classList.add('active');

        // 모든 탭 콘텐츠에 'none' 클래스 추가
        Array.from(tabContainer.children).forEach(tabContent => tabContent.classList.add('none'));

        // 클릭된 버튼의 인덱스 기반으로 해당 탭 콘텐츠 표시
        const index = Array.from(btnWrap.children).indexOf(this);
        tabContainer.children[index].classList.remove('none');
    });
});

/* ------------------------------------------------------------------------------- */
/* ------------------------------------------------------------------------------- */
/*
    아코디언 테이블 구조 예시

    <div class="accordion_table_wrap">
        <div class="accordion_table even hover">
            <ul>
                <li>제목1</li>
                <li>상세1</li>
            </ul>
            <ul>
                <li>제목2</li>
                <li>상세2</li>
            </ul>
        </div>
    </div>
*/
const accordion = document.querySelectorAll('.accordion_table *');
accordion.forEach(item => {
    item.addEventListener('click', function(event) {
        // 첫번째 li 클릭시에만 아코디언 작동
        const row = this.children[0];
        if (event.target !== row) return;

        // 클릭시 다른 li 닫기
        const isOpen = this.classList.contains('open');
        const parent = this.parentElement;
        parent.querySelectorAll('*').forEach(sibling => sibling.classList.remove('open'));
        if (!isOpen) {
            this.classList.add('open');
        }
    });
});
/* ------------------------------------------------------------------------------- */
/* ------------------------------------------------------------------------------- */

/*
    캐러셀(슬라이드)

*/
document.addEventListener('DOMContentLoaded', () => {
    const carousels = document.querySelectorAll('.carousel');

    carousels.forEach(carousel => {
        const itemWrap = carousel.querySelector('.carousel_item_wrap');
        const items = Array.from(carousel.querySelectorAll('.carousel_item'));
        const dotWrap = carousel.querySelector('.carousel_dot_wrap');
        const prevButton = carousel.querySelector('.carousel_prev');
        const nextButton = carousel.querySelector('.carousel_next');

        let currentIndex = 0;

        // 아이템과 관련된 스타일을 계산하여 설정
        const setCarouselStyles = () => {
            const itemWidths = items.map(item => item.offsetWidth);
            const maxItemWidth = Math.max(...itemWidths);
            const totalWidth = maxItemWidth * items.length;

            carousel.style.width = `${maxItemWidth}px`;
            itemWrap.style.width = `${totalWidth}px`;

            items.forEach(item => {
                item.style.width = `${maxItemWidth}px`;
            });
        };

        // Dot 생성
        const createDots = () => {
            dotWrap.innerHTML = '';
            items.forEach((_, index) => {
                const dot = document.createElement('div');
                dot.classList.add('carousel_dot');
                dot.dataset.index = index;
                dotWrap.appendChild(dot);
            });
            updateDots();
        };

        // Dot 업데이트
        const updateDots = () => {
            const dots = dotWrap.querySelectorAll('.carousel_dot');
            dots.forEach(dot => {
                dot.classList.toggle('active', parseInt(dot.dataset.index) === currentIndex);
            });
        };

        // 아이템 이동
        const moveToIndex = (index) => {
            currentIndex = (index + items.length) % items.length;
            itemWrap.style.transform = `translateX(-${currentIndex * 100}%)`;
            updateDots();
        };

        // 이벤트 리스너 추가
        dotWrap.addEventListener('click', (event) => {
            if (event.target.classList.contains('carousel_dot')) {
                const index = parseInt(event.target.dataset.index);
                moveToIndex(index);
            }
        });

        prevButton.addEventListener('click', () => {
            moveToIndex(currentIndex - 1);
        });

        nextButton.addEventListener('click', () => {
            moveToIndex(currentIndex + 1);
        });

        // 초기 설정
        setCarouselStyles();
        createDots();
    });
});

/* ------------------------------------------------------------------------------- */
/* ------------------------------------------------------------------------------- */

/*
    코치 마크

*/

/* ------------------------------------------------------------------------------- */

