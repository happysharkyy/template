<template>
<div class="page-container">

          <div
            v-for="(item, index) in list"
            :key="index"
            class="column is-one-quarter"
            style="float:left"
          >
            <div class="card rb-card">
              <div class="card-image">
                <figure class="image is-4by3">
                  <img :src="url+item.local" style="width:100px;height:100px;">
                </figure>
              </div>
              <div class="card-content">
                <el-button 
                  class="button is-info is-light mr-1 has-text-weight-bold"
                  @click="view(item.id)"
                >
                  点击删除
                </el-button>
              </div>
            </div>
          </div>
</div>
</template>

<script>
import { baseUrl } from '@/utils/global'
export default {
  data() {
    return {
      url:'', 
      list: []
    }
  },
  created() {
    this.url=baseUrl
    this.fetchList()
  },
  methods: {

    fetchList: function() {
      this.$api.image.getImageAll().then((value) => {
        const { data } = value
        this.list = data
      })
    },
    view: function(id) {
      this.$router.push({ name: 'activity-detail', params: { id: id }})
    },
  }
}
</script>

<style scoped>
.rb-card {
    border: none;
    border-radius: 3px;
    /*box-shadow: 5px 5px 16px 0 rgba(0, 0, 0, 0.13);*/
    box-shadow: 0 -0.5em 0em -1.125em rgba(10, 10, 10, 0.1), 0 0px 0 1px rgba(10, 10, 10, 0.02);
    cursor: pointer;
    transition: all 0.1s ease-in-out;
    position: relative;
    overflow: hidden;
}

.rb-card:hover {
    transform: scale(1.03);
}


</style>
