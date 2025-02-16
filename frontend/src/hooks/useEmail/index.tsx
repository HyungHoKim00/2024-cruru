import ApiError from '@api/ApiError';
import emailApis from '@api/domain/email';
import { useToast } from '@contexts/ToastContext';
import { createEmailHistoryQueryKey } from '@hooks/useGetEmailHistory';
import { useMutation, useQueryClient } from '@tanstack/react-query';

export default function useEmail(onSuccess?: () => void) {
  const { success, error } = useToast();
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (prop: { clubId: string; applicantIds: number[]; subject: string; content: string }) =>
      emailApis.send(prop),

    onSuccess: (_, { applicantIds, clubId }) => {
      if (onSuccess) onSuccess();
      success('메일 전송에 성공했습니다');
      queryClient.invalidateQueries({ queryKey: createEmailHistoryQueryKey(clubId, applicantIds[0]) });
    },

    onError: (err) => {
      if (err instanceof ApiError) {
        error(err.message);
      } else {
        error('메일 전송에 실패했습니다');
      }
    },
  });
}
