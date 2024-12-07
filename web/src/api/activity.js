import request from "@/utils/request";

export function pageQuery(page, limit, data) {
  return request({
    url: `/activity/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}

export function deleteById(id) {
  return request({
    url: `/activity/deleteById/${id}`,
    method: 'delete'
  })
}

export function insert(data) {
  return request({
    url: `/activity/insert`,
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: `/activity/update`,
    method: 'put',
    data: data
  })
}
